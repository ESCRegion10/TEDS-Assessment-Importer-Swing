%~d0
cd %~dp0
java -Xms256M -Xmx1024M -cp .;../lib/routines.jar;../lib/log4j-1.2.16.jar;../lib/dom4j-1.6.1.jar;../lib/talendcsv.jar;../lib/talend_file_enhanced_20070724.jar;convertsatcsv_2018_6_0.jar; tedsai_sat_psat_ap.convertsatcsv_2018_6_0.ConvertSATCSV_2018 --context=Default %* 