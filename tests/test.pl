#!/usr/bin/perl

use strict;
use File::Basename;
use Cwd 'abs_path';

my $CWD = dirname(abs_path($0)) . "/";

chdir($CWD);

my $test_type = $ARGV[0];
my $test_file = $CWD . $test_type . ".txt";

die "Must specify (compile|build|run) on the cmd line\n" if(!$test_type);

my $COMPILER = $CWD . '../ShadowCompiler.jar';

die "Cannot find compiler: $COMPILER\n" if(! (-e $COMPILER) );

# open the config file
open(CONFIG, $test_file) or die "Cannot open file $test_file\n$!";

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

my $build_log = $CWD . 'build.log';

# clean-up some files from the last run
system("rm $build_log");
system("rm $CWD" . 'build/*c ' . $CWD . 'build/*.meta') if($test_type ne 'compile');

my $script_ret = 0;

open(RESULTS, ">$CWD" . 'results.log') or die $!;

foreach my $test (@tests) {
	$test->{'config'} = ' -C ' . "$CWD" . $test->{'config'} if($test->{'config'} ne '');
	$test->{'config'} = ' --check ' . $test->{'config'} if($test_type eq 'compile'); # overload this a bit

	my $cmd = "java -jar $COMPILER" . $test->{'config'} . " $CWD$test_type/" . $test->{'file'};

	print $cmd . "\n";

	system("echo \"$cmd\" >> $build_log");

	my $res = system("/usr/bin/time -f \"res: %x, time: %es, avg mem: %Kk max mem: %Mk\" $cmd >> $build_log 2>&1");
	
	system("echo \"\" >> $build_log");

	if($res == $test->{'res'}) {
		print RESULTS "PASS: " . $test->{'file'} . "\n";
	} else {
		print RESULTS "FAIL: " . $test->{'file'} . " EXPECTING " . $test->{'res'} . " GOT " . $res . "\n";
		$script_ret = 1;
	}
}

close(RESULTS);

exit($script_ret);
