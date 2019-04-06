# SplicePort-dev
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

## FAQ
### Question: What can be done to speed up the computation?
Answer: Try to increase the number of threads while executing the command. This can be done by increasing the number next to '-t' option. 
