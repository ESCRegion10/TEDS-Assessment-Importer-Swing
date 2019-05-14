%~d0
cd %~dp0
java -Xms256M -Xmx1024M -cp .;../lib/routines.jar;../lib/log4j-1.2.16.jar;../lib/dom4j-1.6.1.jar;../lib/talendcsv.jar;../lib/talend_file_enhanced_20070724.jar;convertsat_2014_2_0.jar; tedsai_sat_psat_ap.convertsat_2014_2_0.ConvertSAT_2014 --context=Default %* 