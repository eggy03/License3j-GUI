# License3j-GUI

A simple GUI app in Swing, based on Peter Verhas' [License3j](https://github.com/verhas/License3j), a free License
Management Library in Java.
The GUI is based on the [License3j-REPL App](https://github.com/verhas/License3jRepl) which is a CLI Application used to
create key pairs and create and sign licenses.

# Download

You can download pre-built binaries from the [Releases](https://github.com/Egg-03/License3j-GUI/releases/latest) page

# Build

You can also build your own binaries from the source code using Maven

#### Clone the repository locally

```
git clone https://github.com/eggy03/License3j-GUI.git
cd License3j-GUI
```
- Build the application depending on your platform
```shell
./mvnw -Pdist package jpackage:jpackage@win
```
```shell
./mvnw -Pdist package jpackage:jpackage@linux
```
```shell
./mvnw -Pdist package jpackage:jpackage@mac
```

#### Build artifacts using Maven

In the repository directory, run the following command

```
mvn -e clean package
```

# Instructions

Before using this application, it is strongly recommended that you read
the [License3j Readme](https://github.com/verhas/License3j/blob/master/README.adoc) for a better understanding of how
this application works. You can also refer to the [License3j REPL Readme](https://github.com/verhas/License3jRepl)

Upon starting the application, you will see this UI

---

#### 1) Creating a new license in memory

If you are creating a license for the first time, use the *New License* button.
This creates a new license in memory. From here on, you can keep adding features, generate keys to sign, verify and save
your
license.

#### 2) Adding features

You can add any number of features of types available in the combo box

Optional: You can add machine ID to the license feature to make your license bound to a single machine. By default, it
will show the machine ID of the current machine, but you can add your own machineID as well.

---

#### 3) Generating Keys to Sign Your License

Select a Cipher string and a size and press on Generate Keys to create a key-pair in memory

---

#### 4) Signing your license

Once your keys have been loaded and the license is ready to be signed,
press *Sign License* to sign your license
with the key and *Verify License* to verify
your signed license. Verification also works for licenses loaded from a file.

---

#### 5) Digesting/Copying your public key

Digesting the public key will create a Java code output of the public key along with its digest and display it in the
logs.
You will need to embed this code in your application you are creating the
license for. For more information, check out
the [License3j Readme](https://github.com/verhas/License3j/blob/master/README.adoc)

---

#### 6) Saving your license

It is important to save your signed license in the storage or else you will lose it when the application is
closed.

To save your license, select a license format and give it a name along with an extension of your choice and then save
it. File Extension on a license is optional.

---

#### 7) Loading an existing license

You can also load a saved existing license by clicking on *Load License*. Make sure you select the correct type of
license before loading. For example, if your license was saved in a binary form, but you are loading it in a base64
form,
it will throw an error.

---

#### 8) Saving Keys

Provide the names of your keys, select a format and hit *Save Keys*. File Extensions on a keys are optional.

#### 9) Loading existing keys

Similar to license loading, you can load already created keys in memory. Similar to license loading, keys also have
their loading types.

---

#### 10) Logging

Most of the workflow you perform in this application is logged for your convenience.

---

#### 10) Helpful Parameters

These parameters keep a track of the current statuses of the licenses and keys loaded in memory.

---