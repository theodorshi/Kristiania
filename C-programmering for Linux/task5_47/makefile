INCLDIR = ./include
CC = gcc
CFLAGS = -O2
CFLAGS += -I$(INCLDIR)
OBJDIR = obj

_DEPS = ewpdef.h task5.h
DEPS = $(patsubst %,$(INCLDIR)/%,$(_DEPS))

_OBJS = task5.o
OBJS = $(patsubst %,$(OBJDIR)/%,$(_OBJS))

$(OBJDIR)/%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)
   
task5: $(OBJS)
	gcc -o $@ $^ $(CFLAGS)
   
.PHONY: clean
clean:
	rm -f $(OBJDIR)/*.o *~ core $(INCLDIR)/

