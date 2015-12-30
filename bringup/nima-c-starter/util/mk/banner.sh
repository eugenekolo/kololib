#!/bin/bash
# C Starter Banner Printer
# Use project vars (from info.mk) to format header

INFO=()
INFO+=(${#PROJECT})
INFO+=(${#DESCRIPTION})
INFO+=(${#AUTHOR})
INFO+=(${#GROUP})

DATE=`date +"%c"`
INFO+=(${#DATE})


function print_item {
    if [ ! -z "$2" ]; then
        # If alternative width is given, save MAX and overwrite it
        TMPMAX=$MAX
        MAX=$2
    fi

    if [ "$1" == "_" ]; then
        # PAD with 1 to account for extra space
        SPACE=$(( $MAX/2 ))
        PAD=1
    else
        # Trailing spaces should pad by 1 if spacing is odd
        SPACE=$(( ($MAX - ${#1}) / 2 ))
        PAD=$(( ($MAX - ${#1})%2 ))
    fi

    # Trailing spaces (or _)
    for ((i = 0; i < $(( $SPACE + $PAD )); i++)) do
        [ "$1" == "_" ] && echo -n "_" || echo -n " "
    done

    # Item
    [ "$1" == "_" ] && echo -n "" || echo -n "$1"

    # Leading spaces (or _)
    for ((i = 0; i < $(( $SPACE )); i++ )) do
        [ "$1" == "_" ] && echo -n "_" || echo -n " "
    done

    if [ ! -z "$2" ]; then
        # If alternative width is given restore MAX before resuming
       MAX=$TMPMAX
    fi
}


# Find longest string from above
MAX=0
for n in "${INFO[@]}"; do
    (( n > MAX )) && MAX=$n
done

# Real max is $MAX + $PADDING
# (1 - mod(max + padding)) will make sure value is odd
PADDING=20
MAX=$(( ($MAX + $PADDING) + (1 - (($MAX + $PADDING)%2)) ))


# Top line
echo -n "          "
print_item "_"
echo "          "


# Project name line
echo -n "_________|"
print_item "$PROJECT"
echo "|_________"


# Description line
echo -n "\        |"
print_item "$DESCRIPTION"
echo "|        /"


# Author  line
echo -n " \       |"
print_item "$AUTHOR"
echo "|       / "

# Group line
echo -n "  \      |"
print_item "$GROUP"
echo "|      /  "


# DATE line
echo -n "  /      |"
print_item "$DATE"
echo "|      \  "


# Blank line
echo -n " /       |"
print_item "_"
echo "|       \ "


# Bottom line
echo -n "/___________)"
print_item "" $(( $MAX - 6 ))
echo -n "(___________\\"


echo ""
echo ""
echo ""
