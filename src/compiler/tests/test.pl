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

# clean-up some files from the last run
system("rm build.log");
system("rm build/*c build/*.meta") if($test_type ne 'compile');

open(RESULTS, ">results.log") or die $!;

foreach my $test (@tests) {
	$test->{'config'} = ' -C ' . $test->{'config'} if($test->{'config'} ne '');
	$test->{'config'} = ' --check ' . $test->{'config'} if($test_type eq 'compile'); # overload this a bit

	my $cmd = "java -jar $COMPILER" . $test->{'config'} . " $test_type/" . $test->{'file'};

	print $cmd . "\n";

	system("echo \"$cmd\" >> build.log");

	my $res = system("/usr/bin/time -f \"res: %x, time: %es, avg mem: %Kk max mem: %Mk\" $cmd >> build.log 2>&1");
	
	system("echo \"\" >> build.log");

	if($res == $test->{'res'}) {
		print RESULTS "PASS: " . $test->{'file'} . "\n";
	} else {
		print RESULTS "FAIL: " . $test->{'file'} . " EXPECTING " . $test->{'res'} . " GOT " . $res . "\n";
	}
}

