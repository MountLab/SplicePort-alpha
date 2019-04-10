# SplicePort-alpha
SplicePort is a splice-site analysis tool that makes splice-site predictions for a given sequence.

## Installation

#### Setting up the jar file 
- Download the SplicePort package from the repository. (Click on the 'Clone or download' button on the upper right -> "Download ZIP")
- Extract the downloaded zip file (SplicePort-alpha-master.zip).
- You can test the jar file inside the extracted folder by running the command described in the following Execution Command section.
- Successful completion of the execution command confirmed that the jar file is ready to be run on any input sequence file.
- Based on your requirement, you may create new .fasta file and provide it to the jar file using the '-f' option.

#### Source code compilation [OPTIONAL]
The source code is distributed in the "src" folder.
Dependency jar files - 
-   Apache Commons CLI (https://commons.apache.org/proper/commons-cli/download_cli.cgi)
-   Apache Commons IO (https://commons.apache.org/proper/commons-io/download_io.cgi)


## Execution Command
SpliceScorer: Scores possible donor and acceptor sites in a provided fasta file based on how likely they are to be true splicing donor or acceptor sites.
```sh
$ java -jar SpliceportScorer.jar -t 2 -f dummy_seq_file.fasta -d donor_feature_file.txt -a acceptor_feature_file.txt -w 162 -o testout.txt

Options:
-w: Window Size (value should be 162)
-t: Number of Threads(optimally number of processors avialable + 1, default is 2)*
-f: Input Fasta File
-a: Acceptor Feature File (Spliceport 2.0 feature file type)
    You may use the one provided in this package - acceptor_feature_file.txt 
-d: Donor Feature File	(Spliceport 2.0 feature file type)
    You may use the one provided in this package - donor_feature_file.txt
-o: Output file
*Optional Argument
```

## References
- Rezarta Islamaj Dogan, Lise Getoor, W. John Wilbur, Stephen M. Mount, SplicePort—An interactive splice-site analysis tool, Nucleic Acids Research, Volume 35, Issue suppl_2, July 2007, Pages W285–W291, https://doi.org/10.1093/nar/gkm407
- Lipika R Pal, Chen-Hsin Yu, Stephen M Mount, John Moult, Insights from GWAS: emerging landscape of mechanisms underlying complex trait disease, BMC Genomics, 16 (Suppl 8), June 2015, https://doi.org/10.1186/1471-2164-16-S8-S4


## FAQ
### Question: What can be done to speed up the computation?
Answers: 
1) Try to increase the number of threads while executing the command. This can be done by increasing the number next to '-t' option. 
2) Partition a longer sequence into 600 bp pieces at 500 bp intervals (1-600, 501-1100, 1001-1600, etc.).  We recognize that this could be handled in the code itself, but have not yet implemented that. 
