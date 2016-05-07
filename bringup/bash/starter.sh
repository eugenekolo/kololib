#!/bin/bash
while true; do
    echo "enter your name:"
read name
if [[ -z $name ]]
then
echo "bye"
exit 0
fi


case $operand in
    "+") op='+';;
    "-") op='-';;
    "*") op='*';;
    "/") op='/';;
    "%") op='%';;
    "**") op='^';;
    *) op='error';;
esac



ans=$(echo "$n1 $op $n2" | bc -l)


