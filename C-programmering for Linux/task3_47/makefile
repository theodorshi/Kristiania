INCLDIR = ./include
CC = gcc
CFLAGS = -O2
CFLAGS += -I$(INCLDIR)

OBJDIR = obj

_DEPS = examlist.h
DEPS = $(patsubst %,$(INCLDIR)/%,$(_DEPS))

_OBJS = task3.o examlist.o
OBJS = $(patsubst %,$(OBJDIR)/%,$(_OBJS))

$(OBJDIR)/%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)
   
task3: $(OBJS)
	gcc -o $@ $^ $(CFLAGS)
   
.PHONY: clean
clean:
	rm -f $(OBJDIR)/*.o *~ core $(INCLDIR)/

