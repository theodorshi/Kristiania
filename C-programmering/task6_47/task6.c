#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include "ewpdef.h"

#define HEADER_SIZE 20
#define FLAG_ACK 0x10
#define FLAG_NACK 0x20
#define FLAG_FIN 0x01
#define MAX_BUF (1024 * 1024 * 10)  /* 10 MB */


/* Leser nøyaktig iWantBytes fra socket */
int RecvAll(int iSockFd, unsigned char *pbyBuf, int iWantBytes) {
   int iTotalRecv = 0;
   int iRecv = 0;
   
   while (iTotalRecv < iWantBytes) {
      iRecv = recv(iSockFd, pbyBuf + iTotalRecv, iWantBytes - iTotalRecv, 0);
      if (iRecv <= 0) {
         return -1;
      }
      iTotalRecv += iRecv;
   }
   return iTotalRecv; 
} /* RecvAll Slutt */

/* Sender en svar-pakke (ACK eller NACK) */
void SendResponse(int iSockFd, unsigned int uiSeq, unsigned char ucFlag) {
   struct EWA_EXAM25_TASK4_PROTOCOL_TCP stResp;
   memset(&stResp, 0, sizeof(stResp));
   stResp.uiAckNumber = uiSeq;
   stResp.ucFlags = ucFlag;
   send(iSockFd, &stResp, HEADER_SIZE, 0);
} /* SendResponse Slutt */

/* =============================================================================
====================================== main ================================= */
int main(int iArgc, char *apszArgv[]) {
   char *pszServerIp = NULL;
   int iPort = 0;
   int i = 0;
   struct sockaddr_in stServer = {0};
   int iSockFd = 0;
   unsigned char *pbyFileBuffer = NULL;
   unsigned char abyHeader[HEADER_SIZE];
   unsigned char *pbyData = NULL;
   int iDone = 0;
   long lTotalBytes = 0;
   FILE *fOut = NULL;
   struct EWA_EXAM25_TASK4_PROTOCOL_TCP *pstPkt = NULL;
   
   for (i = 1; i < iArgc; i++) {
      if (strcmp(apszArgv[i], "-server") == 0) {
         pszServerIp = apszArgv[i + 1];
      }
      if (strcmp(apszArgv[i], "-port") == 0) {
         iPort = atoi(apszArgv[i + 1]);
      }
   }
   
   /* Sjekker for å unngå å kjøre programmet uten argumenter (følge NULL-peker) */
   if (pszServerIp == NULL || iPort == 0) {
      printf("Bruk: ./task6 -server <ip> -port <port>\n");
      return 1;
   }
   
   /* Oppretter socket */
   iSockFd = socket(AF_INET, SOCK_STREAM, 0);
   if (iSockFd < 0) {
      printf("Feil: kunne ikke opprette socket");
      return 1;
   }
   
   /* Fyller inn serveradresse */
   memset(&stServer, 0, sizeof(stServer));
   stServer.sin_family = AF_INET;
   stServer.sin_port = htons(iPort);
   stServer.sin_addr.s_addr = htonl(0x7F000001);
   
   /* Kobler til */
   if (connect(iSockFd, (struct sockaddr *)&stServer, sizeof(stServer)) < 0) {
      printf("Feil: kunne ikke koble til server\n");
      return 1;
   }
   printf("Koblet til %s: %d\n",  , iPort);
   
   pbyFileBuffer = (unsigned char *)malloc(MAX_BUF);
   if (pbyFileBuffer == NULL) {
      printf("Feil: malloc feilet");
      return 1;
   }
   
   while (!iDone) {
      
      /* Leser header */
      if (RecvAll(iSockFd, abyHeader, HEADER_SIZE) < 0) {
         break;
      }
      
      pstPkt = (struct EWA_EXAM25_TASK4_PROTOCOL_TCP *)abyHeader;
      
      /* Leser data */
      pbyData = (unsigned char *)malloc(pstPkt->usSizeOfPacket);
      if (RecvAll(iSockFd, pbyData, pstPkt->usSizeOfPacket) < 0) {
         break;
      }
      
      /* Sender ACK */
      SendResponse(iSockFd, pstPkt->uiSequenceNumber, FLAG_ACK);
      
      /* Kopier data inn i buffer */
      memcpy(pbyFileBuffer + pstPkt->uiSequenceNumber,
             pbyData, pstPkt->usSizeOfPacket);
      
      /* Oppdater total størrelse */
      if ((long)(pstPkt->uiSequenceNumber + pstPkt->usSizeOfPacket)
                 > lTotalBytes) {
         lTotalBytes = pstPkt->uiSequenceNumber + pstPkt->usSizeOfPacket;           
      }
      
      /* Sjekker FIN */
      if (pstPkt->ucFlags & FLAG_FIN) {
         iDone = 1;
      }
      
      free(pbyData);
      pbyData = NULL;
   }
   
   /* Skriver til fil */
   fOut = fopen("task6_received.bmp", "wb");
   if (fOut != NULL) {
      fwrite(pbyFileBuffer, 1, lTotalBytes, fOut);
      fclose(fOut);
      printf("Fil lagret: %ld bytes\n", lTotalBytes);
   }
   
   free(pbyFileBuffer);
   pbyFileBuffer = NULL;
   close(iSockFd);
}
/* main slutt */



