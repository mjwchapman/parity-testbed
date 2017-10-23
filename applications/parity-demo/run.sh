#!/bin/bash
cd "$(dirname "$0")"
java -jar parity-demo.jar etc/devel.conf 2>&1 | tee parity-demo.log
