# Online-Scheduler

## Compiling Java files using Eclipse IDE

1. Download this repository as ZIP
2. Create new `Java Project` in `Eclipse`
3. Right click on your `Java Project` --> `Import`
4. Choose `General` --> `Archive File`
5. Put directory where you downloaded ZIP in `From archive file`
6. Put `ProjectName/src` in `Into folder`
7. Click `Finish`

## Linking the jUnit library

8. Right click on your `Java Project` --> `Build Path` --> `Add Libraries`
9. Choose `jUnit` --> `Next`
10. Choose `jUnit 4` as the version
11. Click `Finish`

## Running the program

1. Right click on your `Java Project` --> `Run As` --> `Java Application`

## Problem Description

We have `m` machines to use and will be given an unknown number of `jobs`. There are two classes of job: `simple jobs` that take 'five' minutes to complete and `complex jobs` that take 'ten' minutes. Your system is given one job at a time and must assign it a slot in the schedule immediately before receiving information about the next job. We assume that assigning a job takes no time, that all jobs are independent, that the next job can begin running on a machine as soon as the previous job has completed, and that all jobs arrive before any begin to run. 

## Goal

The goal is to schedule all jobs so that the final job to complete finishes as soon as possible

## Notes

- The problem is solved using an `approximation algorithm` and `probabilistic algorithm`
- `Testing.java` produces a file `<dirName>_test_results.csv` which shows the `average running times` in `ms` of the program using different parameters
- See proof, results and algorithm <a href='https://github.com/rjperez94/Online-Scheduler/blob/master/Report.pdf'>here</a>
