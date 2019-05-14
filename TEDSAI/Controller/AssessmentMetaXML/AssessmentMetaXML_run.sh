#!/bin/sh
cd `dirname $0`
ROOT_PATH=`pwd`
java -Xms256M -Xmx1024M -cp .:$ROOT_PATH:$ROOT_PATH/../lib/routines.jar:$ROOT_PATH/../lib/jaxen-1.1.1.jar:$ROOT_PATH/../lib/log4j-1.2.16.jar:$ROOT_PATH/../lib/dom4j-1.6.1.jar:$ROOT_PATH/../lib/thashfile.jar:$ROOT_PATH/../lib/talendcsv.jar:$ROOT_PATH/../lib/external_sort.jar:$ROOT_PATH/../lib/talend_file_enhanced_20070724.jar:$ROOT_PATH/assessmentmetaxml_6_0.jar: tedsai_sat_psat_ap.assessmentmetaxml_6_0.AssessmentMetaXML --context=Default "$@" 