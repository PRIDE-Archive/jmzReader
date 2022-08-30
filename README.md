jmzReader
===============
[![Java CI with Maven](https://github.com/PRIDE-Utilities/jmzReader/actions/workflows/maven.yml/badge.svg)](https://github.com/PRIDE-Utilities/jmzReader/actions/workflows/maven.yml)

# About jmzReader

The jmzReader library is a collection of Java APIs to parse the most commonly used MS peak list formats. Currently, the library contains parsers for
 
* dta
* mgf
* ms2
* mzData
* mzXML
* pkl
* mzML
* PRIDE XML

# License

 jmzReader is a PRIDE API licensed under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt).

# How to cite it:

Griss J, Reisinger F, Hermjakob H, Vizcaíno JA. jmzReader: A Java parser library to process and visualize multiple text and XML-based mass spectrometry data formats. Proteomics. 2012 Mar;12(6):795-8.

# Main Features

All parsers are optimized to be used in conjunction with mzIdentML. Based on a custom build class to efficiently parse text files line by line all parsers can handle arbitrary large files in minimal memory, allowing easy and efficient processing of peak
list files using the Java programming language. mzIdentML files do not contain spectra data but refer to external peak list files. 
All peak list parsers support the methods used by mzIdentML to reference external spectra and implement a common interface. 
Thus, when developing software for mzIdentML programmers no longer have to support multiple peak list file formats but only
this one interface.



# The library provides four key modules:

Apart of the file format readers the jmzReader library comes with an additional tool: the jmzReader-gui.

jmzReader-gui: The jmzReader-gui is a simple tool to visualize peak list files. It supports all file formats supported by the jmzReader
library and demonstrates the usage of the jmzReader interface. Furthermore, it can export all of the supported file formats into 
the commonly used mgf format.

**Note**: the library is still evolving, we are committed to expand this library and add more useful classes.

# Getting jmzReader

The zip file in the releases section contains the jmzReader jar file and all other required libraries.

Maven Dependency

To add all parsers to you maven project simply add the following dependencies to your project's . jmzReader library can be used in
 Maven projects, you can include the following snippets in your Maven pom file.
 
 ```maven
 <dependency>
        <groupId>uk.ac.ebi.pride.tools</groupId>
        <artifactId>jmzreader</artifactId>
        <version>version</version>
        <type>jar</type>
</dependency>
 ```

```maven
 <repository>
             <id>pst-release</id>
             <name>EBI Nexus Repository</name>
             <url>hhttps://ww.ebi.ac.uk/Tools/maven/repos/content/repositories/pst-release</url>
 </repository>
   <!-- EBI SNAPSHOT repo -->
         <repository>
             <id>pst-snapshots</id>
             <name>EBI Nexus Snapshots Repository</name>
             <url>https://www.ebi.ac.uk/Tools/maven/repos/content/repositories/pst-snapshots</url>
         </repository>
```
Note: you need to change the version number to the latest version.

For developers, the latest source code is available from our SVN repository.

# Getting Help

If you have questions or need additional help, please contact the PRIDE Helpdesk at the EBI: pride-support at ebi.ac.uk (replace at with @).

Please send us your feedback, including error reports, improvement suggestions, new feature requests and any other things you might want to suggest to the PRIDE team.

# This library has been used in:

* Perez-Riverol Y., Uszkoreit J., Sanchez A., Ternent T., Del Toro N., Hermjakob H., Vizcaíno J.A., Wang R. ms-data-core-api: an open-source, metadata-oriented library for computational proteomics. Bioinformatics, 2015 Sep 1;31(17):2903-5 [ms-data-core-api](http://bioinformatics.oxfordjournals.org/content/31/17/2903.long)
* Côté, R. G., Griss, J., Dianes, J. A., Wang, R., Wright, J. C., van den Toorn, H. W., ... & Vizcaíno, J. A. (2012). The PRoteomics IDEntification (PRIDE) Converter 2 framework: an improved suite of tools to facilitate data submission to the PRIDE database and the ProteomeXchange consortium. Molecular & Cellular Proteomics, 11(12), 1682-1689. [PRIDE Converter 2](https://code.google.com/p/pride-converter-2/) 
* Vizcaíno, J. A., Côté, R. G., Csordas, A., Dianes, J. A., Fabregat, A., Foster, J. M., ... & Hermjakob, H. (2013). The PRoteomics IDEntifications (PRIDE) database and associated tools: status in 2013. Nucleic acids research, 41(D1), D1063-D1069. [PRIDE-Archive](http://www.ebi.ac.uk/pride/archive/)

