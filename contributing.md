# Contributing <!-- omit in toc -->
- [Starting up](#starting-up)
- [Workflow](#workflow)
  - [Branching](#branching)
    - [Creating a branch](#creating-a-branch)
    - [Branch Naming](#branch-naming)
    - [Pushing your branch](#pushing-your-branch)
    - [Listing Branches](#listing-branches)
    - [Changing Branches](#changing-branches)
    - [Transferring changes from master to branch](#transferring-changes-from-master-to-branch)
    - [Transferring changes from branch to master](#transferring-changes-from-branch-to-master)
    - [Deleting Branches](#deleting-branches)
- [Pull-Requests](#pull-requests)
- [Development Environment](#development-environment)
- [Testing](#testing)
- [Documentation](#documentation)
- [Coding Standards](#coding-standards)
- [Issues & Queries](#issues--queries)

This is a basic outline of how to add code to this repository. Generally, a contributing file on Github is used when people submit [pull requests](#pull-requests) or create [issues](#issues), those functionalities will still be there. However, we will be using this as a way to breakdown the structure of our development pipeline and code conventions.

## Starting up
Go to the folder you want to develop in and run:
```
git clone https://github.com/AngusTheMack/SQLmark.git
```
This will download the files from the SQLmark repository and add them to your working direction. `clone` is simply a method for downloading the repository contents and initializing a repository with the correct git history, and it authorizes the user as well.

This will also set the branch to **master**. In order to check if everything worked you can simply `cd` into SQLmark and run `git status`. And it should look as follows:
```
capstone@uct:/$ cd SQLmark/
capstone@uct:/SQLmark$ git status
On branch master
Your branch is up-to-date with 'origin/master'.
nothing to commit, working directory clean
capstone@uct:/SQLmark$
```

That is the basic startup, now lets get into the workflow.

## Workflow
If something is going on the **master** branch it should be:
* Reviewed by a teammate
* Tested
* Compilable
* Deployable

This is because master is the base on which all the following deployments/changes will be made. This workflow also allows for changes to be made on your own private branch without fear of breaking the product. This workflow is the [ Github-flow](https://help.github.com/articles/github-flow/) workflow. 

Effectively, you create a branch to do your work on, once finished you submit a [pull request](#pull-requests) for it - it gets reviewed, if it is okay you merge it back into master.
### Branching
#### Creating a branch
Before you create your own branch, it is best to ensure that the code on your machine is as up to date as possible with master. So, for safety sake run a `git pull` to ensure there are no lingering changes.

Then to create your own branch you simply need to run:
```git
git checkout -b <branch name>
```
This will create a branch on your local with whatever you inserted in `<branch name>`, and then changes your current branch from master to the other branch.

The command is pretty descriptive, you basically stop *checking out* the current branch and start *checking out* the branch you want to switch to. The `-b` flag tells git to create a new branch to *checkout* with the given name
#### Branch Naming
A good practice when branching is to use a branch name that is a short descriptor of the work you are doing. So if you are implementing a java-mysql connection class, a good branch name might be as follows:
```git
git checkout -b java-mysql-connector
```
To demonstrate the functionality of the command, here is output from running the example above:
```bash
capstone@uct:/SQLmark$ git checkout -b java-mysql-connector
Switched to a new branch 'java-mysql-connector'
capstone@uct:/SQLmark$ git status
On branch java-mysql-connector
nothing to commit, working directory clean
```
As you can see after running the `git status` command, we are definitely on the `java-mysql-connector` branch. 

`git status` is a handy way of keeping track of which branch you are on, and whether you have any changes to commit.
#### Pushing your branch
It is a good practice to upload your code to the repo for various reasons. To do this you simply need to run:
```
git push --set-upstream origin java-mysql-connector
```
This will push your current working changes to the online repo. You can view the code on the github website by navigating to the repo homepage, and clicking on the `Branch: master` button, as shown below:
![Git Branch](projectImages/gitbranch.png)

The command is pretty descriptive as well. `push` is used to notify the git client to move your code to the online repo. `--set-upstream` tells the git client which remote service you are going to be using. `origin` is the name of the remote service, and `java-mysql-connector` is the name of the branch. Once this command has been run for your branch you simply need to run `git push` and it will remember what the upstream was set to.

#### Listing Branches
To list the branches that are in your current git repo you can run 
```
git branch
```
with no other flags and all the branches will appear. Here is the output from the above example:
```
capstone@uct:/SQLmark$ git branch
* java-mysql-connector
  master
```

#### Changing Branches
Changing branches uses the `checkout` command. 
From the example above, we have two branches and are currently on the `java-mysql-connector` branch. In order to change over to `master` you would run the command.
```
git checkout master
```

#### Transferring changes from master to branch
If you are working on your branch, but master has been updated and you want 
to work with the most recent code.
Simply run:
```
git merge master
```
in order to add the master branch's updates to your code. Here is an example:
```
capstone@uct:/SQLmark$ git checkout test-plan
Switched to branch 'test-plan'
Your branch is up to date with 'origin/test-plan

capstone@uct:/SQLmark$ git merge master
Merge made by the 'recursive' strategy.
README.md                |  31 +++++++++-
contributing.md          | 153 ++++++++++++++++++++++++++++++++++-------------
docs/README.md           |  64 ++++++++++++++++++++
src/README.md            |  19 ++++++
src/controller/README.md |   8 +++
src/model/README.md      |   9 +++
src/view/README.md       |   9 +++
test/README.md           |  10 ++++
8 files changed, 261 insertions(+), 42 deletions(-)
create mode 100644 docs/README.md
create mode 100644 src/README.md
create mode 100644 src/controller/README.md
create mode 100644 src/model/README.md
create mode 100644 src/view/README.md
create mode 100644 test/README.md
```
You will have to push your code onto the remote again to include the merged changes.

**Note:** if the changes on master are within the same file as a change on your branch
 there is a high chance of merge failures; so understand that it might not be entirely straightforward, 
but if you resolve the merge conflicts it should make it easier to push into
`master` eventually.
#### Transferring changes from branch to master
This must only be done if the following requirements have been met.
* A teammate has reviewed the code to be added
* There is 100% path coverage in unit testing
* All tests pass
* It compiles successfully
* It can be deployed

Checkout the master branch, run a `git pull` on that for safety as well. Then run:
```
git checkout master
git merge <branch-name>
```

Where branch-name is the name of the branch you have been working on. We will mainly stick to using `git merge`, however there are more methods available.

For instance, another method of adding the branch to `master` and that is via the `git rebase` command. You can run:
```
git checkout <branch-name>
git rebase master
```

For the above example this will be as follows:
```
capstone@uct:/SQLmark$ git checkout java-mysql-connector
capstone@uct:/SQLmark$ git rebase master
First, rewinding head to replay your work on top of it...
Fast-forwarded java-mysql-connector to master.
```
You can verify these changes by simply looking at the files you changed to see if they are still there.

**How rebaseing works** - From [git](https://git-scm.com/book/en/v2/Git-Branching-Rebasing)
> It works by going to the common ancestor of the two branches (the one you’re on and the one you’re rebasing onto), getting the diff introduced by each commit of the branch you’re on, saving those diffs to temporary files, resetting the current branch to the same commit as the branch you are rebasing onto, and finally applying each change in turn.

A possible use case for rebasing within our project is if there is a fix that was very necessary to get something working. The commits from that fix would be useful on `master` - thus, it would be better to use `rebase` rather than a simple `merge` in order to ensure that those commits are on `master`

**Merges Failures** git is not entirely perfect. If two copies of the same code have different changes it doesn't know which one to choose. There is a chance of this happening on any project. With the `git rebase` command, this can be a problem because you have to sort through every commit you made on the branch and verify if it has any merge conflicts on it before it can rebase. Which can be tedious. Thus, it is generally easier to use merge - and it does mean that there are less commits on `master`.
#### Deleting Branches
There might be a situation where you want to delete a branch. To delete a local branch you can run:
```
git branch -d <branch-name>
```
To delete the branch if it is on the remote you can run:
```
git push <remote-name> --delete <branch-name>
```
If we were to follow on from the previous examples the local command would be as follows:
```
git branch -d java-mysql-connector
```
Which gives the output:
```
capstone@uct:/SQLmark$ git branch -d java-mysql-connector
Deleted branch java-mysql-connector (was a8c39ba).
```
Then for the remote deletion you would run:
```
git push origin --delete java-mysql-connector
```
which gives the output:
```
capstone@uct:/SQLmark$ git push origin --delete java-mysql-connector
Username for 'https://github.com': AngusTheMack
Password for 'https://AngusTheMack@github.com':
To https://github.com/AngusTheMack/SQLmark.git
 - [deleted]         java-mysql-connector
 ```

## Pull-Requests
**Note:**
Please ensure that the pull request adheres to all specifications laid out in this document.

To submit a pull request for a branch, simply navigate to the *Pull Request* [page](https://github.com/AngusTheMack/SQLmark/pulls), click the **New Pull Request** [button](https://github.com/AngusTheMack/SQLmark/compare), choose the branch you have been working on (you will have had to push it to the online repository first in order to do this) and click the **Create Pull Request** button.

If you want to create a pull request for a specific issue, please `fork` the repo and create a pull request from that fork. Once you log a pull request I will also try to get back to you as soon as possible. 

## Development Environment
We are using IntelliJ ultimate in order to create and run our code.
## Testing
`JUnit` is the test framework used for this project. `JUnit` is simple to use and has a vast amount of documentation available. 

**Note:** There are some packages you *may* need to add to the classpath in order to get `JUnit` to work with IntelliJ. [Here](https://www.jetbrains.com/help/idea/configuring-testing-libraries.html) is a walkthrough on how to add them if you run into difficulty.

Here is the basic format of a `JUnit` test:
```java
class FirstJUnitTest {

    @Test
    void myFirstTest() {
        assertEquals(2, 1 + 1);
    }

}
```

Here are some resources to help!
* [User Guide](https://junit.org/junit5/docs/current/user-guide/)
* [Documentation](https://junit.org/junit5/docs/current/api/overview-summary.html)
* [Vogella Tutorial](http://www.vogella.com/tutorials/JUnit/article.html#junittesting)
## Documentation
We will use javadocs in order to create and maintain documentation for all our code. Please use the javadocs [notation](https://www.oracle.com/technetwork/java/javase/tech/index-137868.html) when writing all your code. For example:
```java
/**
  * Lorem ipsum dolor sit amet, consectetur adipiscing elit...
  * @author John Doe
  * @version 07/08/2018
  */
public class foo{
  // example
}
```

The beginnig of the class has a brief description - *Lorem ipsum* etc, the writer of the class - `@author`, and the version - `07/08/2018` which is the date

```java
/**
  * Lorem ipsum dolor sit amet, consectetur adipiscing...
  * @param foo the variable fizzbuzz takes
  * @return the string and FizzBuzz
  */
public String FizzBuzz(String foo){
  String bar = foo + " FizzBuzz!";
  return bar
}
```
There are a few other javadocs keywords we might use, like
`@exception`, `@see`, etc. Please use them where necessary.

Please take a look at the [docs](/docs) folder for more information.
## Coding Standards
Add more rules as we go along:
* Use descriptive variable names

## Issues & Queries
If you have any issues, please don't hesitate to create one [here](https://github.com/AngusTheMack/SQLmark/issues) and I will attempt to get back to it as soon as possible.

If you have any other queries at all, please feel free to contact me [here](mailto:mckang009@myuct.ac.za)
