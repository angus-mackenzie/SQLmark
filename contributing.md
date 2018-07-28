# Contributing
This is a basic outline of how to add code to this repository Generally, a contributing file on Github is used when people submit pull requests or create issues - we will be using them as a way to simplify the structure of our development pipeline.

## Starting up
Go to the folder you want to develop in and run:
'''
git clone https://github.com/AngusTheMack/SQLmark.git
'''
This will download the files from the SQLmark directory and add them to your working direction.

This will also set the branch to **master**. In order to check if everything worked you can simply `cd` into SQLmark and run `git status`. And it should look as follows:
```
<insert output here>
```

That is the basic startup, now lets get into the workflow.

## Workflow
If something is going on the **master** branch it should be:
* Reviewed by everyone
* Unit Tested
* Compilable
* Deployable

This is because master is the base on which all the following deployments/changes will be made. It also allows for changes to be made on your own private branch without fear of breaking the product.

### Branching
Before you create your own branch, it is best to ensure that the code on your machine is as up to date as possible with master. So, for safety sake run a `git pull` to ensure there are no lingering changes.

Then to create your own branch you simply need to run:
```git
git checkout -b <branch name>
```
This will create a branch on your local with whatever you inserted in `<branch name>`, and change the current branch from master to that branch.

A good practice when branching is to use a branch name that is a short descriptor of the work you are doing. So if you are implementing a java-mysql class, a good branch name might be as follows:
```git
git checkout -b java-mysql-connector
```
To demonstrate the functionality of the command, here is output from running the example