#!/bin/bash
# Starter Update Script
# nhmood @ [gooscode] labs
# April 7th, 2014

# Check out update branch, pull, and get update date
git remote add starter $(cat util/starter/source)
git checkout -b starter_update
git pull starter master
DATE=`git log -1 --format="%ci" | cut -d ' ' -f1`
COMMIT=`git rev-parse --short master`

# Remove init.sh and info.mk, we want to keep ours
rm init.sh
git rm init.sh
git checkout --ours info.mk

# Do the actual merge in the branch
git add . --all
git commit -m "Merge starter_update"

# Squash commits in branch down to one
# Soft reset to first commit (keeps working tree untouched)
git reset $(git rev-list --max-parents=0 starter/HEAD)
git add . --all
git commit --amend -m "Update from C Starter [#$COMMIT] ($DATE)"

# Checkout master again
git checkout master
git merge --no-ff --no-commit starter_update

# Remove remote
git remote rm starter
