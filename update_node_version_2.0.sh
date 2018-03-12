#!/bin/bash

repos=( "git@github.com:parmar-gaurav/nodejs.git" 
        "git@github.com:parmar-gaurav/test_repo.git" )

for i in "${repos[@]}"
do 
   NEW_UUID=$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 7 | head -n 1) 
 ssh -i "$GIT_SSH_KEYS_PATH"  git clone -b  development --single-branch $i  clone_$NEW_UUID
 done


#apt-get install npm
npm install

for i in `ls|grep clone`; 
do 
cd $i
npm version patch
echo "--- Pushing $i"; #cd $i; 
ssh -i "$GIT_SSH_KEYS_PATH" git push; cd ..; 
echo "--- Finished $i"; 
done