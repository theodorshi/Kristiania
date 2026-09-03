INCLDIR = ./include
CC = gcc
CFLAGS = -O2
CFLAGS += -I$(INCLDIR)

OBJDIR = obj

_DEPS = task4_prime.h
DEPS = $(patsubst %,$(INCLDIR)/%,$(_DEPS))

_OBJS = task4_threads.o task4_prime.o xtea.o ipc.o
OBJS = $(patsubst %,$(OBJDIR)/%,$(_OBJS))

$(OBJDIR)/%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

$(OBJDIR)/ipc.o: ipc.c $(DEPS)
# lagt til c99 stddef.h for å ikke endre selve filen
	$(CC) -c -o $@ $< -O2 -I$(INCLDIR) -std=c99 -Wall -include stddef.h
   
task4: $(OBJS)
	gcc -o $@ $^ $(CFLAGS) -lpthread   # -lpthread lagt til for bruk av threads
   
.PHONY: clean
clean:
	rm -f $(OBJDIR)/*.o *~ core $(INCLDIR)/

