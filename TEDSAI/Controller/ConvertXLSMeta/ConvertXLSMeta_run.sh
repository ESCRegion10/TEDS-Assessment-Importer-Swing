#!/bin/sh
cd `dirname $0`
ROOT_PATH=`pwd`
java -Xms256M -Xmx1024M -cp .:$ROOT_PATH:$ROOT_PATH/../lib/routines.jar:$ROOT_PATH/../lib/commons-lang-2.6.jar:$ROOT_PATH/../lib/geronimo-stax-api_1.0_spec-1.0.1.jar:$ROOT_PATH/../lib/log4j-1.2.15.jar:$ROOT_PATH/../lib/log4j-1.2.16.jar:$ROOT_PATH/../lib/xmlbeans-2.6.0.jar:$ROOT_PATH/../lib/dom4j-1.6.1.jar:$ROOT_PATH/../lib/poi-ooxml-schemas-3.11-20141221.jar:$ROOT_PATH/../lib/poi-ooxml-3.11-20141221_modified_talend.jar:$ROOT_PATH/../lib/talendcsv.jar:$ROOT_PATH/../lib/poi-3.11-20141221_modified_talend.jar:$ROOT_PATH/../lib/poi-scratchpad-3.11-20141221.jar:$ROOT_PATH/convertxlsmeta_5_0.jar: tedsai_sat_psat_ap.convertxlsmeta_5_0.ConvertXLSMeta --context=Default "$@" 