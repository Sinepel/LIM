#!/usr/bin/perl

$message = $ARGV[0];

open(TODO, ">>TODO.list");
print TODO "$message\n";

close(TODO);
