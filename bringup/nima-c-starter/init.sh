#!/bin/bash
# Starter Init Script
# nhmood @ [gooscode] labs
# April 7th, 2014

# Query about project info (avoid filling in info.mk manually)
# Add src/ to .gitignore (so won't try to merge src/ on pull)
# Remove init script when finished

echo "-== Starter Template Init Script ==-"
echo -n "Would you like to populate info.mk from this script? (Y/n): "
read POPULATE
echo ""

if [ "$POPULATE" == "Y" ] || [ "$POPULATE" == "y" ]; then
    PROJECT=""
    while [ -z "$PROJECT" ]; do
        echo -n "Project Name: "
        read PROJECT
    done

    DESCRIPTION=""
    while [ -z "$DESCRIPTION" ]; do
        echo -n "Description: "
        read DESCRIPTION
    done

    AUTHOR=""
    while [ -z "$AUTHOR" ]; do
        echo -n "Author: "
        read AUTHOR
    done

    GROUP=""
    while [ -z "$GROUP" ]; do
        echo -n "Group: "
        read GROUP
    done

    INFO=$(cat <<MSG
PROJECT         = $PROJECT
DESCRIPTION     = $DESCRIPTION
AUTHOR          = $AUTHOR
GROUP           = $GROUP


# External libraries
# LIBS are the compile flags for the libraries
# LIBLIST are the names of targets for the libraries (i.e. util/mk/curl.mk --> curl)
# LIB_VERSION is the version corresponding to each lib, used for util/mk/curl.mk but varies by lib
#
# e.g. LIBS         = -lcurl
#      LIBLIST      = curl
#      CURL_VERSION = 7.28.0
LIBS            =
LIBLIST         =
CURL_VERSION    =
MSG
            )

    echo "$INFO" > info.mk
    echo ""
    echo "Look in info.mk for instructions on how to include external libraries / dependencies"

fi

# Remove this script and add it to .gitignore
rm init.sh
git rm init.sh


# Check in the new info.mk and .gitignore and starter url
git add info.mk
git add .gitignore

git config --get remote.origin.url >> util/starter/source
git add util/starter/source
git commit -m "info.mk, .gitignore, and starter source"

# Soft reset to first commit (keeps working tree untouched)
DATE=`git log -1 --format="%ci" | cut -d ' ' -f1`
COMMIT=`git rev-parse --short master`
git reset $(git rev-list --max-parents=0 HEAD)
git add . --all
git commit --amend -m "Initialize from C Starter [#$COMMIT] ($DATE)"

# Remove starter remote
git remote rm origin
