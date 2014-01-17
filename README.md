# Quality Control Entities

This project implements entities maintained by the PhenoDCC QC system. The
quality control system maintains a history of all the data that have been
collected and processed by the Data Coordination Centre at
[MRC Harwell](http://har.mrc.ac.uk/), including all of the quality control
issues that have been raised, resolved and are pending resolution.
The `phenodcc_qc` database keeps track of all of this information.

## Database schema and documentation

The database schema for the `phenodcc_qc` database is defined in
`src/main/resources/phenodcc_qc.sql`. This script also provides further
documentation on the architecture and rationale underlying the QC system.

## Notes

* In the root source directory, all of the entities are stored as Java templates
  inside the `tsrc` directory. The actual entities are generated during
  compilation depending on the Maven profile selected from `pom.xml`. This is to
  cater to database name differences between profiles.

* This project includes two Java classes `Sessions.java` and
  `SessionsPK.java` which are entities from the Drupal instance database used by
  [https://www.mousephenotype.org](https://www.mousephenotype.org). We have done
  this merely for convenience. These entities are used when checking active
  login sessions when serving REST web service requests.

