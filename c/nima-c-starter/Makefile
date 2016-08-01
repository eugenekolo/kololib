# Makefile
# starter - c starter build
# nhmood @ [goosecode labs] - March 12th, 2014 

# Info: 
# Makefile for building starter and all of its components

# Include info.mk which has project parameters
include info.mk

# Include recursive wildcard function makefile
include util/mk/rwildcard.mk

# Make project info parameters accessible in banner generator
export PROJECT
export DESCRIPTION
export AUTHOR
export GROUP


# Compiler settings
# -Wall 					- Enable all warning flags
# -Wextra					- Some extra warning flags not enabled by -Wall
# -Wpedantic				- Enable warnings based of ISO standards
# -Wformat=2				- Checks string formatting
# -Wno-format-nonliteral	- Warn if not string literal which can't be checked
# -Wshadow					- Warn if var shadows another
# -Wpointer-arith			- Warns when using sizeof on func type of void
# -Wcast-qual				- Warn about casting to remove type qualifier
# -Wmissing-prototypes 		- Warn if global function is missing prototype
# -Wno-missing-braces		- Turn off warning for fully bracketed expressions
# -std						- Which standard of C to use
# -O2						- Turn on all non space-speed tradeoff optimizations
CC			= gcc
CFLAGS		= -Wall -Wextra -Wpedantic -Wformat=2 -Wno-format-nonliteral \
			  -Wshadow -Wpointer-arith -Wcast-qual -Wmissing-prototypes \
			  -Wno-missing-braces -std=gnu99 -O2
IFLAGS		= -I$(EXT)/include
LDFLAGS		= -L$(EXT)/lib



# Build settings
DIR			= $(CURDIR)
SRCDIR		= $(DIR)/src
STAGE 		= $(DIR)/stage
EXT			= $(STAGE)/3rdparty
PKG			= $(EXT)/pkg
COMPILE		= $(STAGE)/obj
RELEASE		= $(DIR)/bin

HOST		= i686-linux-gnu
BUILD		= i686-linux-gnu


# Source files
# Use wildcard to get all .c files in src
# Use $(subst) to modify directory for .o files
SRC			= $(call rwildcard, $(SRCDIR)/, *.c)
OBJ			= $(patsubst $(SRCDIR)/%.c, $(COMPILE)/%.o, $(SRC))
EXE			= $(PROJECT) 


all: info prep $(PROJECT)


prep: dirs $(LIBLIST)


info:
	util/mk/banner.sh


dirs:
	@echo "==> dirs"
	@echo "[!] Creating required directories"
	@echo ""
	mkdir -p $(STAGE)
	mkdir -p $(EXT)
	mkdir -p $(PKG)
	mkdir -p $(COMPILE)
	mkdir -p $(RELEASE)
	@echo ""
	@echo ""


compile_msg:
	@echo "==> $(PROJECT)"
	@echo "[!] Compiling source under $(SRCDIR)"

compile_done:
	@echo "[o] Source compiled to $(COMPILE), proceeding to linking"
	@echo ""

# starter 
# Use starter.o as main object (instead of debugBuild.o)
# The $(patsubst) removes debugBuild.o from the build command
$(PROJECT): prep compile_msg $(OBJ) compile_done
	@echo "[o] Dependencies built, proceeding to $(PROJECT)"
	@echo "[!] Linking object files"
	@echo ""
	@echo "[!] Object files used"
	@echo "=> $(notdir $(OBJ))"
	@echo ""
	$(CC) $(CFLAGS) $(IFLAGS) $(LDFLAGS) $(LIBS) $(patsubst $(COMPILE)/debugBuild.o,,$(OBJ)) -o $(RELEASE)/$(PROJECT)
	@(test -f $(EXE) && true || ln -s $(RELEASE)/$(EXE) .)
	@echo ""


# debugBuild
# Use debugBuild.o as main object (instead of dropbox++)
# The $(patsubst) removes dropboxpp.o from the build command 
debug: prep $(OBJ) 
	@echo "Building debugBuild"
	@echo "Linking $(notdir $(OBJ))"
	$(CC) $(CFLAGS) $(IFLAGS) $(LDFLAGS) $(LIBS) $(patsubst $(COMPILE)/$(PROJECT).o,,$(OBJ)) -o $(RELEASE)/debugBuild 
	@echo ""


# Generic wildcard target for compiling .o files from corresponding .c file
# Use .o target in stage/obj and .c in src
# -c 	- Produce object files
# $< 	- Automatic variable for first prerequisite
# -o 	- Change name
# $(@F) - Filename without preceding directory
# $* - Stem (%) from target (src/%.c --> %)
$(COMPILE)/%.o: $(SRCDIR)/%.c
	@echo "[o] Compiling $*.c"
	mkdir -p $(@D)
	$(CC) $(CFLAGS) $(IFLAGS) -c $< -o $(COMPILE)/$*.o
	@echo ""


# Clean only object and release files, keep pkg and source and directories intact
clean: info
	@echo "==> clean"
	@echo "[!] Cleaning object and release files"
	@echo ""
	rm -rf $(EXE)	
	rm -rf $(COMPILE)
	rm -rf $(RELEASE)
	@echo ""
	@echo "[o] Object and release files cleaned"
	@echo ""

# Full clean of project directory, remove stage and release
rm: info
	@echo "==> rm"
	@echo "[!] Removing object, release, and external dependency files"
	@echo ""
	rm -rf $(EXE)
	rm -rf $(PKG)
	rm -rf $(STAGE)
	rm -rf $(EXT)
	rm -rf $(COMPILE)
	rm -rf $(RELEASE)
	@echo ""
	@echo "[o] Object, release, and external dependency files files cleaned"
	@echo ""


	@echo ""


# Include Makefile for additional dependencies
include util/mk/curl.mk
