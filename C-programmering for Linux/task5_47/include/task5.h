#ifndef TASK_5_H
#define TASK_5_H

/* Maks størrelse på server-id string (-id argument) */
#define MAX_SERVER_ID 64

/* Maks størrelse på filnavn som mottas fra klienten */
#define MAX_FILENAME 64

/* Maks størrelse på fil-innhold - 9998 bytes per chunk (ewpdef.h), 64KB buffer */
#define MAX_FILE_SIZE 65536

/* Hvor mange tilkoblinger kan hope seg opp mens de venter på accept() */
#define SERVER_BACKLOG 5

/* Bruker enum for å sikre kjent returnverdi */
enum eMenu {
   HELO,
   MAIL_FROM,
   RCPT_TO,
   DATA_CMD,
   RECV_FILE,
   DATA_OR_QUIT
};

/* ParseArgs */
int ParseArgs(int iArgc, char *apszArgv[], int *piPort, char *pszServer,
                int iServerIdSize);

/* HandleClient */
void HandleClient(int iClientFd, const char *pszServerId,
                    const char *pszClientIp);

/* CreateServer */           
int CreateServer(int iPort);

/* SendAll */
int SendAll(int iSockFd, const char *pcBuf, int iLen);

/* RecvExact */
int RecvExact(int iSockFd, char *pcBuf, int iLen);

/* IsValidFilename */
int IsValidFilename(const char *pszName);

#endif



