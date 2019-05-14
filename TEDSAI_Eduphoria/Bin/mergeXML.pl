#!/usr/bin/perl 

#---------------------------------------------------------------------------------------------
#
# Script:  mergeXML.pl
#
# Purpose: merge two XML files to create one XML file.
#
# Usage:   This program merges the top of one XML file with the bottom of
#	   another XML file, forming a validly formed final XML file.
#
#          mergeXML.pl file1 file2 final_file
#
# Example: perl mergeXML.pl beginXMLfile.xml endXMLfile.xml finalXMLfile.xml
#
# Notes:   The first file is the 'top' of the final XML file and the second file is the 'bottom'
#          of the final XML file.
#
# Revision History:
# Date		Author		Remarks
# 20110215	T. Esposito	original version.
# 20160101	T. Esposito	used to merge/create StudentAssessment.xml file. 
#				assessment data mart project.  
# 20180328      T. Esposito	added file open error checking.
# 20180329	T. Esposito	added code to send Perl STDERR to separate log file in current working folder.
#
#---------------------------------------------------------------------------------------------

use strict;
use warnings;
use Cwd;

my $curdir = getcwd();

local $SIG{__DIE__} = sub {
    my ($message) = @_;
    open(FH4, ">", "$curdir" . "\\..\\..\\Logs\\" . "perl_error.log");
    print FH4 $message;
};

open(FH0, "<$ARGV[0]") or die "Can't open file $ARGV[0]: $!";    # first XML file
open(FH1, "<$ARGV[1]") or die "Can't open file $ARGV[1]: $!";    # second XML file
open(FH2, ">$ARGV[2]") or die "Can't open file $ARGV[2]: $!";    # final XML file

while(<FH0>) {                                  	# read first file
  next if $_ =~ "</InterchangeStudentAssessment";   	# exclude closing tag
  next if $_ =~ m/^\s*$/;                       	# skip blank lines
  print FH2;                                    	# write to final file
}

while(<FH1>) {
  next if $_ =~ "<?xml version=";               	# read second file
  next if $_ =~ "<InterchangeStudentAssessment";    	# exclude xml header and beginning tag
  next if $_ =~ m/^\s*$/;                       	# skip blank lines
  print FH2;                                    	# write to final file
}

exit(0);
__END__