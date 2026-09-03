#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include "task5.h"
#include "ewpdef.h"

/* Kjør programmet med: ./task5 -port 2525 -id SmtpTest */

/* ---------------------------------- ParseArgs --------------------------------
 - Parser -port og -id fra argv
 - Sjekker om i+1 < iArgC før jeg leser apszArgv[i+1] */
 
int ParseArgs(int iArgc, char *apszArgv[], int *piPort, char *szServerId,
                int iServerIdSize) {
   int i = 0;
   int iPortFound = 0;
   int iIdFound = 0;
   
   /* Initialisering av output til en trygg default */                
   *piPort = 0;
   szServerId[0] = '\0';
   
   for (i = 1; i < iArgc; i++) {
      
      /* Ser etter -port-flagg, deretter les neste argv som verdien */
      if (strncmp(apszArgv[i], "-port", 5) == 0) {
         
         /* Passer på at det er et nytt argument før jeg leser det */
         if (i + 1 >= iArgc) {
            fprintf(stderr, "Error: -port-flagg gitt, men ingen verdi\n");
            return -1;
         }
         i++;
         *piPort = atoi(apszArgv[i]);
         
         /* Verifiserer at porten er gyldig */
         if (*piPort <= 0 || *piPort > 65535) {
            fprintf(stderr, "Error: port %s er ikke gyldig port-nummer\n",
                    apszArgv[1]);
            return -1;
         }
         iPortFound = -1;
      }
      else if (strncmp(apszArgv[i], "-id", 3) == 0) {
         if (i + 1 >= iArgc) {
         fprintf(stderr, "Error: -id trenger en verdi\n");
         return -1;
         }
         i++;
         
         /* snprintf for å kopiere bruker-data trygt, siden det
            alltid nulltermineres, så lenge størrelse > 0 */
         snprintf(szServerId, iServerIdSize, "%s", apszArgv[i]);
         iIdFound = 1;
      }
   } /* for-loop slutt */
   
   /* Sjekker at jeg har begge argumentene for å kjøre programmet */
   if (!iPortFound || !iIdFound) {
      fprintf(stderr, "Error: mangler påkrevd argument\n");
      
      if (!iPortFound) {
         fprintf(stderr, "Error: mangler påkrevd argument -port(2525)\n");
      }
      if (!iIdFound) {
         fprintf(stderr, "Error: mangler påkrevd argument -id(SmtpTest)\n");
      }
      printf("Kjør programmet med: ./task5 -port 2525 -id SmtpTest\n");
      return -1;
   }
   return 0;   /* Suksess */
} /* ParseArgs Slutt
----------------------------------------------------------------------------- */


/* =============================================================================
================================Hjepefunksjoner=================================
============================================================================= */

/* --------------------------------- CreateServer ------------------------------
 - socket, bind, listen */
int CreateServer(int iPort) {
   int iSockFd = -1;
   struct sockaddr_in saServer = {0};
   
   iSockFd = socket(AF_INET, SOCK_STREAM, 0);
   if (iSockFd < 0) {
      perror("socket()");
      return -1;
   }
   
   saServer.sin_family = AF_INET;
   saServer.sin_port = htons((unsigned short)iPort);  /* htons - sikre riktig port*/
   saServer.sin_addr.s_addr = INADDR_ANY;
   
   /* Knytter adressen og porten ved å kalle på bind() og listen() */
   if (bind(iSockFd, (struct sockaddr *)&saServer, sizeof(saServer)) < 0) {
      perror("bind()");
      close(iSockFd);
      return -1;
   }
   
   if (listen(iSockFd, SERVER_BACKLOG) < 0) {
      perror("listen()");
      close(iSockFd);
      return -1;
   }
   
   printf("Lytter på 127.0.0.1:%d\n", iPort);
   return iSockFd;
} /* CreateServer Slutt */


/* ----------------------------------- SendAll ---------------------------------
 - Sender hele structen over nettverket til klieneten 
 - Looper til alt er sent (iLeft == 0)
*/
int SendAll(int iSockFd, const char *pcBuf, int iLen) {
   int iSent = 0;   /* Antall bytes sendt */
   int iLeft = iLen;   /* Antall bytes som gjenstår */
   int iN = 0;   /* Returverdien fra send() - Bytes sendt i dette kallet */
   
   while (iLeft > 0) {
      /* Starter på det som gjenstår å sende */
      iN = (int)send(iSockFd, pcBuf + iSent, (size_t)iLeft, 0);
      if (iN < 0) {
         perror("send()");
         return -1;
      }
      iSent += iN;   /* Oppdaterer totalt sendt */
      iLeft -= iN;   /* Reduserer gjenstående */
   }
   return 0;
} /* SendAll Slutt 
------------------------------------------------------------------------------*/


/* ----------------------------------- RecvExact -------------------------------
 - Looper gjennom til nøyaktig bytes fra iLen er mottatt
 - recv() returnerer 0 hvis klienten har koblet fra */
int RecvExact(int iSockFd, char *pcBuf, int iLen) {
   int iRecv = 0;   /* Antall bytes mottatt */
   int iLeft = iLen;
   int iN = 0;
   
   while (iLeft > 0) {
      /* pcBuf + iRecvd = neste ledige posisjon bufferen */
      iN = (int)recv(iSockFd, pcBuf + iRecv, (size_t)iLeft, 0);
      if (iN < 0) {
         perror("recv()");
         return -1;
      }
      if (iN == 0) {
         printf("Klient frakoblet\n");
         return -1;
      }
      iRecv += iN;
      iLeft -= iN;
   }
   return 0;
} /* RecvExact Slutt 
------------------------------------------------------------------------------*/


/* ------------------------------------ SendReply ------------------------------
 - Fyller ServerReply og sender den
 - Hjelper for ulike responser (250, 354, 221, 501) */
int SendReply(int iClientFd, const char *pszCode, const char *pszMsg) {
   struct EWA_EXAM25_TASK5_PROTOCOL_SERVERREPLY stReply;
   
   /* Nullstiller hele structen - unngår søppelverdier */
   memset(&stReply, 0, sizeof(stReply));
   
   /* Fyller header-feltene fra ewpdef.h 
      Kopierer "EWP" inn i struct-feltet i minnet */
   memcpy(stReply.stHead.acMagicNumber, EWA_EXAM25_TASK5_PROTOCOL_MAGIC, 3);
   memcpy(stReply.stHead.acDataSize, "0064", 4);
   stReply.stHead.acDelimeter[0] = '|';
   memcpy(stReply.acStatusCode, pszCode, 3); /* 250, 354, 221, 501, osv. */
   stReply.acHardSpace[0] = ' ';
   snprintf(stReply.acFormattedString,
            sizeof(stReply.acFormattedString), "%s", pszMsg);
   stReply.acHardZero[0] = '\0';   /* Sikrer lesbar string med nullterminering */
   
   printf("[SEND] %s %s\n", pszCode, pszMsg);
   return SendAll(iClientFd, (const char *)&stReply, (int)sizeof(stReply));
} /* SendReply Slutt 
------------------------------------------------------------------------------*/


/* -------------------------------- SendServerAccept ---------------------------
 - Sendre 220-banner når klienten kobler til
 - Bekreftelse på at server er klar
 - Bruker SERVERACCEPT fra ewpdef.h med statuskode 220 */
int SendServerAccept(int iClientFd, const char *szServerId) {
   struct EWA_EXAM25_TASK5_PROTOCOL_SERVERACCEPT stAccept;
   memset(&stAccept, 0, sizeof(stAccept));
   
   /* Fyller SIZEHEADER - magic "EWP", størrelse "0064", delimeter "|" */
   memcpy(stAccept.stHead.acMagicNumber, EWA_EXAM25_TASK5_PROTOCOL_MAGIC, 3);
   memcpy(stAccept.stHead.acDataSize, "0064", 4);
   stAccept.stHead.acDelimeter[0] = '|';
   memcpy(stAccept.acStatusCode, "220", 3);
   stAccept.acHardSpace[0] = ' ';
   
   /* Bygger string med server-ID fra kommandolinjen */
   snprintf(stAccept.acFormattedString, sizeof(stAccept.acFormattedString),
            "127.0.0.1 SMTP %s", szServerId);
   stAccept.acHardZero[0] = '\0';
   
   printf("[SEND] 220 %s\n", stAccept.acFormattedString);
   return SendAll(iClientFd, (const char *)&stAccept, (int)sizeof(stAccept));
} /* SendServerAccept Slutt 
------------------------------------------------------------------------------*/


/* --------------------------------- IsValidFilename ---------------------------
 - Sjekker at filnavn er trygt
 - Blokkerer path traversal: .. eller /
 -  */
int IsValidFilename(const char *pszName) {
   int iLen = 0;
   int iDots = 0;
   int i = 0;
   int c = 0;
   
   if (pszName == NULL) {
      return 0;
   }
   
   iLen = (int)strlen(pszName);
   if (iLen == 0 || iLen >= MAX_FILENAME) {
      return 0;   /* For kort eller for langt */
   }
   if (strchr(pszName, '/') != NULL) {
      return 0;   /* Path traversal */
   }
   if (strstr(pszName, "..") != NULL) {
      return 0;   /* Path traversal */
   }
   
   /* Sjekker hvert tegn - kun a-z, A-Z, 0-9, -, _, . er tillatt */
   for (i = 0; i < iLen; i++) {
      c = pszName[i];
      if (c == '.') {
         iDots++;
         continue;
      }
      if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ||
         (c >= '0' && c <= '9') || c == '-' || c == '-')) {
            return 0;   /* Ulovlig tegn funnet */
         }
   }
   
   /* Filnavn må ha nøyaktig ett punktum */
   if (iDots != 1) {
      return 0;
   }
   /* Må slutte på .eml */
   if (iLen < 5 || strncmp(pszName + iLen - 4, ".eml", 4) != 0) {
      return 0;
   }
   return 1;
} /* IsValidFilename Slutt 
------------------------------------------------------------------------------*/


/* ---------------------------------- HandleClient -----------------------------
 - SMTP session */
void HandleClient(int iClientFd, const char *szServerId,
                    const char *pszClientIp) {
   enum eMenu eCurrentSelection = HELO;
   char szFileName[MAX_FILENAME] = {0};
   char szFileContent[MAX_FILE_SIZE] = {0};
   char szReplyMsg[64] = {0};
   int iDone = 0;
   int iTotalSize = 0;
   int iChunkSize = 0;
   FILE *fOut = NULL;
   
   struct EWA_EXAM25_TASK5_PROTOCOL_CLIENTHELO stHelo;
   struct EWA_EXAM25_TASK5_PROTOCOL_MAILFROM stMailFrom;
   struct EWA_EXAM25_TASK5_PROTOCOL_RCPTTO stRcptTo;
   struct EWA_EXAM25_TASK5_PROTOCOL_CLIENTDATACMD stDataCmd;
   struct EWA_EXAM25_TASK5_PROTOCOL_CLOSECOMMAND stQuit;
   struct EWA_EXAM25_TASK5_PROTOCOL_SIZEHEADER stChunkHead;
   
   /* Sender 220 banner så fort klienten får tilkobling */
   if (SendServerAccept(iClientFd, szServerId) != 0) {
      return;
   }
   
   while (iDone == 0) {
      /* - Peek på første 12 bytes (header=8 + command=4)
           for å identifisere melding 
           MSG_PEAK etterlater data i buffer */
      char acPeek[12] = {0};
      char szCmd[5] = {0};
      int iPeeked = 0;
      
      iPeeked = (int)recv(iClientFd, acPeek, sizeof(acPeek), MSG_PEEK);
      if (iPeeked < 8) {
         fprintf(stderr, "Peek failed\n");
         
         return;
      }
      
      /* Validerer 'magic number' før jeg går videre */
      if (strncmp(acPeek, EWA_EXAM25_TASK5_PROTOCOL_MAGIC, 3) != 0) {
         fprintf(stderr, "Bad magic: %.3s\n", acPeek);
         
         return;
      }
      
      memcpy(szCmd, acPeek + 8, 4);   /* command kommer rett etter 8-byte header */
      szCmd[4] = '\0';
      printf("[RECV] %.4s (selection %d)\n", szCmd, (int)eCurrentSelection);
      
      
      /* switch */
      switch (eCurrentSelection) {
         
         /* Første kommando fra klienten må være HELO, ellers avvises det med 503 */
         case HELO:
            if (strncmp(szCmd, "HELO", 4) != 0) {
               SendReply(iClientFd, "503", "Expected HELO");
               break;
            }
            
            memset(&stHelo, 0, sizeof(stHelo));
            if (RecvExact(iClientFd, (char *)&stHelo, (int)sizeof(stHelo))!= 0) {
            return;
            }
            
            /* Svarer med 250 og klientens IP + brukernavn fra HELO-meldingen */
            snprintf(szReplyMsg, sizeof(szReplyMsg), "%.15s Hello %.40s",
                     pszClientIp, stHelo.acFormattedString);
            if (SendReply(iClientFd, "250", szReplyMsg) != 0) {
               return;
            }
            eCurrentSelection = MAIL_FROM;   /* Går videre til neste kommando */
            break;
         
         /* Avsenderadresse - godtar e-post uten validering */   
         case MAIL_FROM:
            if (strncmp(szCmd, "MAIL", 4) != 0) {
               SendReply(iClientFd, "503", "Forventet MAIL FROM");
               break;
            }
            
            memset(&stMailFrom, 0, sizeof(stMailFrom));
            if (RecvExact(iClientFd, (char *)&stMailFrom,
            (int)sizeof(stMailFrom)) != 0) {
               return;
            }
            
            snprintf(szReplyMsg, sizeof(szReplyMsg), "Sender ok: %.50s",
                     stMailFrom.acFormattedString);
            if (SendReply(iClientFd, "250", szReplyMsg) != 0) {
               return;
            }
            eCurrentSelection = RCPT_TO;
            break;
         
         /* Mottakeradresse - godtar e-post uten validering */   
         case RCPT_TO:
            if (strncmp(szCmd, "RCPT", 4) != 0) {
               SendReply(iClientFd, "503", "Forventet RCPT TO");
               break;
            }
            
            memset(&stRcptTo, 0, sizeof(stRcptTo));
            if (RecvExact(iClientFd, (char *)&stRcptTo,
            (int)sizeof(stRcptTo)) != 0) {
               return;
            }
            
            snprintf(szReplyMsg, sizeof(szReplyMsg), "Recipient ok: %.48s",
                     stRcptTo.acFormattedString);
                     
            if (SendReply(iClientFd, "250", szReplyMsg) != 0) {
               return;
            }
            eCurrentSelection = DATA_CMD;
            break;
            
         case DATA_CMD:   /* Fall-through - deler kode med DATA_OR_QUIT */
         case DATA_OR_QUIT:
            if (strncmp(szCmd, "QUIT", 4) == 0) {
               /* QUIT - Lukker session */
               memset(&stQuit, 0, sizeof(stQuit));
               if (RecvExact(iClientFd, (char *)&stQuit,
                  (int)sizeof(stQuit)) != 0) {
                  return;
               }
               
               SendReply(iClientFd, "221", "Hadet bra");
               printf("[INFO] QUIT - session ferdig\n");
               
               iDone = 1;
               break;
            }
            
            if (strncmp(szCmd, "DATA", 4) != 0) {
               SendReply(iClientFd, "503", "Forventet DATA eller QUIT");
               break;
            }
            
            memset(&stDataCmd, 0, sizeof(stDataCmd));
            if (RecvExact(iClientFd, (char *)&stDataCmd,
               (int)sizeof(stDataCmd)) != 0) {
               return;
            }
            
            memset(szFileName, 0, sizeof(szFileName));
            snprintf(szFileName, sizeof(szFileName), "%s",
                     stDataCmd.acFormattedString);
                     
            
            if (IsValidFilename(szFileName)) {
               snprintf(szReplyMsg, sizeof(szReplyMsg),
                        "Ready for: %s", szFileName);
                        
               if(SendReply(iClientFd, "354", szReplyMsg) != 0) {
                  return;
               }
               else {
                  snprintf(szReplyMsg, sizeof(szReplyMsg),
                           "Ugyldig filnavn: %.30s", szFileName);
                           
                  SendReply(iClientFd, "501", szReplyMsg);
                  }
               }
               break;
         
         /*
         - Mottar data i en eller flere chunks til linjeskift
         - Fastsatt buffer-størrelse, siden maks størrelse er 9998 bytes */
         case RECV_FILE:
            szFileContent[0] = '\0';
            iTotalSize = 0;
            
            while (strstr(szFileContent, "\r\n.\r\n") == NULL) {
            
               /* Leser 8-byte chuck header for å finne antall bytes som følger */
               if (RecvExact(iClientFd, (char *)&stChunkHead,
                               (int)sizeof(stChunkHead)) != 0) {
                  return;                
               }
               
               iChunkSize = atoi(stChunkHead.acDataSize);
               if (iChunkSize <= 0 || iChunkSize > 9998) {
                  fprintf(stderr, "Bad chunk size: %d\n", iChunkSize);
                  
                  return;
               }
               
               /* Sjekker at jeg ikke får overflow i buffer */
               if (iTotalSize + iChunkSize >= MAX_FILE_SIZE) {
                  fprintf(stderr, "Filen er for stor for bufferen");
                  
                  SendReply(iClientFd, "550", "File too large");
                  return;
               }
               
               if (RecvExact(iClientFd, szFileContent + iTotalSize,
                               iChunkSize) != 0) {
                     return;             
               }
               
               iTotalSize += iChunkSize;
               szFileContent[iTotalSize] = '\0';
               printf("[RECV] chunk %d bytes (total %d)\n", iChunkSize, iTotalSize);
               
            }
            
            /* Skriver til fil - fwrite med iTotalSize */
            fOut = fopen(szFileName, "w");
            if (fOut == NULL) {
               perror("fopen");
               SendReply(iClientFd, "550", "Could not save file");
               return;
            }
            fwrite(szFileContent, 1, (size_t)iTotalSize, fOut);
            fclose(fOut);
            
            printf("[INFO] lagret: %s (%d bytes)\n", szFileName, iTotalSize);
            
            if (SendReply(iClientFd, "250", "Fil lagret") != 0) {
               return;
            }
            eCurrentSelection = DATA_OR_QUIT;
            break;
            
         default:
            fprintf(stderr, "Unexpected command: %.4s\n", szCmd);
            SendReply(iClientFd, "503", "Dårlig sekvens med kommandoer");
            break;
      } /* switch slutt */
      
      
   } /* while slutt */
   
   
} /* HandleClient Slutt 
------------------------------------------------------------------------------*/

/* ======================================main================================ */
 
 
/* Beskrivelse: Ingangspunkt. Parser argumenter, lager server socket, godkjenner
              en klient og håndterer SMTP-sesjonen.
              
   Returns:     0 ved clean exit, 1 ved error
============================================================================= */

int main(int iArgc, char *apszArgv[]) {
   int iPort = 0;
   char szServerId[MAX_SERVER_ID] = {0};
   int iServerFd = -1;
   int iClientFd = -1;
   struct sockaddr_in saClient = {0};
   int iClientSize = 0;
   char szClientIp[16] = {0};
   
   /* Her sørger jeg for at jeg har fått riktig argument */
   if (ParseArgs(iArgc, apszArgv, &iPort, szServerId, MAX_SERVER_ID) != 0){
      fprintf(stderr, "Bruk: %s -port <nummer> -id <navn>\n", apszArgv[0]);
      return 1;
   }
   
   printf("Task5 server starter...\n");
   printf("Server ID  : %s\n", szServerId);
   printf("Port       : %d\n", iPort);
   printf("Knyttes til: 127.0.0.1\n\n");
   
   /* Her opprettes lytte-socketen (CreateServer kommer deretter) */
   iServerFd = CreateServer(iPort);
   if (iServerFd < 0) {
      fprintf(stderr, "Error: kunne ikke opprette server på port %d\n", iPort);
      return 1;
   }
   
   printf("Venter på forbindelse på port %d...\n", iPort);
   
   /* 
      accept() - blokker her til EWA får forbindelse.
      
      accept() returnerer en helt ny socket fd for den spesifike klienten.
      Den originale iServerFd lytter fortsatt (mer nyttig med flere klienter).
      Jeg bruker iClientSize som både input (buffer size) og output (faktisk størrelse).
   */
   iClientSize = (int)sizeof(saClient);
   iClientFd = accept(iServerFd, (struct sockaddr *)&saClient,
                      (socklen_t *)&iClientSize);
   if (iClientFd < 0) {
      perror("accept() mislykket");
      close(iServerFd);
      return 1;
   }
   HandleClient(iClientFd, szServerId, szClientIp);
   
   printf("Klient tilkoblet fra: %s\n", szClientIp);
   
   /* Opprydding */
   close(iClientFd);
   close(iServerFd);
   printf("\nServer legges ned\n");
   
   return 0;
} /* main Slutt
----------------------------------------------------------------------------- */



