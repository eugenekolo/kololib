###################################################################################################
# kolobyte bashrc
# @-`->---

BGREEN='\[\033[1;32m\]'
DGREEN='\[\033[0;32m\]'
BRED='\[\033[1;31m\]'
RED='\[\033[0;31m\]'
BBLUE='\[\033[1;36m\]'
BLUE='\[\033[0;34m\]'
NORMAL='\[\033[00m\]'
BPURPLE='\[\033[1;35m\]'
DPURPLE='\[\033[1;34m\]'
WHITE='\[\033[0;37m\]'
BWHITE='\[\033[1;37m\]'

# Aliases and functions
alias gs="git status"
alias gd="git status"
alias gc="git commit -m"
alias ga="git add"
alias gpom="git push origin master"
alias gitdiff="git diff"
alias update="sudo apt-get update && sudo apt-get upgrade"
alias wgetclone="wget --no-clobber --convert-links --random-wait -r -p -E -e robots=off -U mozilla"
alias myscp="rsync --progress --partial"
alias pls='sudo `fc -n -l -1`' # Pls just sudo
alias traceroute="mtr "
alias myip="ifconfig | grep 'inet ' | grep -v 127.0.0.1 | cut -d\   -f2"

extract () {
    if [ -f $1 ]; then
        case $1 in
            *.tar.bz2)  tar -jxvf $1                        ;;
            *.tar.gz)   tar -zxvf $1                        ;;
            *.bz2)      bunzip2 $1                          ;;
            *.dmg)      hdiutil mount $1                    ;;
            *.gz)       gunzip $1                           ;;
            *.tar)      tar -xvf $1                         ;;
            *.tbz2)     tar -jxvf $1                        ;;
            *.tgz)      tar -zxvf $1                        ;;
            *.zip)      unzip $1                            ;;
            *.ZIP)      unzip $1                            ;;
            *.pax)      cat $1 | pax -r                     ;;
            *.pax.Z)    uncompress $1 --stdout | pax -r     ;;
            *.Z)        uncompress $1                       ;;
            *)          echo "'$1' cannot be extracted/mounted via extract()" ;;
        esac
    else
        echo "'$1' is not a valid file"
    fi
}

up () {
    if [[ "$#" < 1 ]] ; then
        cd ..
    else
        CDSTR=""
        for i in {1..$1} ; do
            CDSTR="../$CDSTR"
        done
        cd $CDSTR
    fi
}

# Java stuff
export PATH=$HOME/code/jdk8u60/bin:$HOME/code/jdk8u60/jre/bin:$PATH
export JAVA_HOME=$HOME/code/jdk8u60

# Android stuff
export PATH="/home/eugenek/apps/android-studio/bin:$PATH"
export PATH="/home/eugenek/apps/Android/Sdk/platform-tools:$PATH"

# Python stuff
export PYTHONDONTWRITEBYTECODE=1
export WORKON_HOME=$HOME/.virtualenvs
source /usr/local/bin/virtualenvwrapper.sh

# Ruby stuff
export PATH="$HOME/.rbenv/bin:$PATH"

# MISC
export PATH="/home/eugenek/apps:$PATH" # Add self made bins
export PATH="/home/eugenek/code/sec-tools/bin:/home/eugenek/code/sec-tools:$PATH" # DO NOT EDIT This is added by sec-tools
export CTF_ROOT="/home/eugenek/code/sec-tools" # DO NOT EDIT This is added by sec-tools

# The most important one
PS1="${debian_chroot:+($debian_chroot)}${BGREEN}\u${BWHITE}:${BPURPLE}\w ${BWHITE}▪️ ️${DPURPLE}${NORMAL} "
#PS1='${debian_chroot:+($debian_chroot)}\[\033[01;32m\]\u@\h\[\033[00m\]:\[\033[01;34m\]\w\[\033[00m\]\$ '
