# kolo bash_profile

export PYTHONDONTWRITEBYTECODE=1

# CLI tools
alias pls='sudo `fc -n -l -1`' # Pls just sudo
function up {
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

# Networking ones
alias sniffhttp="ngrep -d 'wlan0' -t '^(GET|POST) ' 'tcp and port 80'" # view HTTP traffic
# Doing a fast proxy, good for watching netflix and youtube without restrictions
alias proxy='ssh -C2qTnN -D 8080'
# Use like `forward eugenek@eugenekolo.com`
alias forward='ssh -L 4242:localhost:4242' 
alias traceroute="mtr "
alias serve='python -m SimpleHTTPServer 8000'
# Quickly get my ip:
alias myip="ifconfig | grep 'inet ' | grep -v 127.0.0.1 | cut -d\   -f2"

# Programming ones
# You can run e.g. 'gdbrun =python test.py' and debug C extensions more easily:
alias gdbrun='gdb -ex=r --args'
alias delpyc='find . -type f -name "*.pyc" -delete' # Delete pyc files

# Fun ones
alias matrix="tr -c \"[:digit:]\" \" \" < /dev/urandom | dd cbs=\"$COLUMNS\" conv=unblock | GREP_COLOR=\"1;32\" grep --color \"[^ ]\""
alias youtube-dl='youtube-dl -ci --restrict-filenames -f 22/18/45/35/h264-sd/flv/mp4-sd/mp4/best '
alias youtube-dlm='youtube-dl --extract-audio --audio-format mp3 -o "$HOME/Downloads/music/%(title)s.%(ext)s" '

