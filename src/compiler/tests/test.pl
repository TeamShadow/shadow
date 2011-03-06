#!/usr/bin/perl

use strict;

my $test_type = $ARGV[0];

die "Must specify (compile|build|run) on the cmd line\n" if(!$test_type);

my $COMPILER = '../ShadowCompiler.jar';

die "Cannot find compiler\n" if(! (-e $COMPILER) );

# open the config file
open(CONFIG, $test_type . ".txt") or die "Cannot open file $test_type.txt\n$!";

my @tests = ();

# go through and parse each line
while(my $line = <CONFIG>) {
	chomp($line);

	next if($line =~ /^#/); # remove comments
	next if($line =~ /^\s*$/); # remove blank lines
	
	my %fields = ();

	($fields{'res'},
	 $fields{'file'},
	 $fields{'config'},
	 $fields{'input'},
	 $fields{'ouput'},
	 $fields{'desc'}) = split(/\s*;\s*/, $line);

	push(@tests, \%fields);
}

close(CONFIG);

foreach my $test (@tests) {
	$test->{'config'} = ' -C ' . $test->{'config'} if($test->{'config'} ne '');

	my $cmd = "java -jar $COMPILER -M $test_type/" . $test->{'file'} . $test->{'config'};

	print $cmd . "\n";

	system($cmd);
}

