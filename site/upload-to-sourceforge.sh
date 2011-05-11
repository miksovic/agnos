#!/bin/sh

make clean
make html
if [ $? -ne 0 ]; then
    echo "--make html failed--"
    exit 1
fi


#rsync -r -v _build/html/ gangesmaster,agnos@shell.sourceforge.net:/home/groups/a/ag/agnos/htdocs
rsync -r -v _build/html/ gangesmaster,agnos@web.sourceforge.net:htdocs/

if [ $? -ne 0 ]; then
    echo "--rsync failed--"
    exit 1
fi
