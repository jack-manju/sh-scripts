#!/bin/bash
now=$(date +"%m-%d-%y")
#echo $now
echo "Security Patches"  >  /var/log/systemupdates_log/secupd_$now.txt
echo "Security Patch installed on:" $( /bin/date +%c ) >> /var/log/systemupdates_log/secupd_$now.txt
echo "################################################################################################"
apt-get -s dist-upgrade | grep "^Inst" | grep -i securi | awk -F " " {'print $2'} | xargs apt-get install >> /var/log/systemupdates_log/secupd_$now.txt