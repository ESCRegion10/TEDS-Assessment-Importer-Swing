%~d0
cd %~dp0
java -Xms256M -Xmx1024M -cp .;../lib/routines.jar;../lib/jaxen-1.1.1.jar;../lib/log4j-1.2.16.jar;../lib/dom4j-1.6.1.jar;../lib/thashfile.jar;../lib/talendcsv.jar;../lib/external_sort.jar;../lib/talend_file_enhanced_20070724.jar;eduphoria_assessmentmetaxml_26_0.jar; tedsai_gui_eduphoria.eduphoria_assessmentmetaxml_26_0.Eduphoria_AssessmentMetaXML --context=Default %* 