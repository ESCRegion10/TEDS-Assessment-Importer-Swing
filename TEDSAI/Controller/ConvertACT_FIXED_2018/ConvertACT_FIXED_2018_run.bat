%~d0
cd %~dp0
java -Xms256M -Xmx1024M -cp .;../lib/routines.jar;../lib/log4j-1.2.16.jar;../lib/dom4j-1.6.1.jar;../lib/talendcsv.jar;../lib/talend_file_enhanced_20070724.jar;convertact_fixed_2018_5_2.jar; tedsai_sat_psat_ap.convertact_fixed_2018_5_2.ConvertACT_FIXED_2018 --context=Default %* 