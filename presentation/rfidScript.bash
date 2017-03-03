#!/bin/bash
stty -a /dev/ttyAMA0 9600 cs8
while true
	do dd if=/dev/ttyAMA0 of=/var/run/tagCode.code bs=8
done
