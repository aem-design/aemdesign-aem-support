AEM Design Initial Content
==============================

This project deploy initial content for AEM.Design

Content Cleaning
=======

Please clean your content when checking it into codebase, use Regex search and replace in the IDE to replace all matching lines with empty string.

You will need to do this couple of time. At very least please remove Account and Unique ID info.


# Replace all identifier attributes these with ""

```
^.+(cq|jcr)\:(createdBy|lastReplicatedBy|lastModifiedBy|lastReplicated|lastReplicationAction|uuid)\=\".+\"\n
^.+(cq|jcr)\:(createdBy|lastReplicatedBy|lastModifiedBy|lastReplicated|lastReplicationAction|uuid)\=\".+\"\>
```

# Remove all system generated attributes

```
^.+(cq|jcr)\:(created|lastReplicated|lastModified|lastReplicationAction|uuid)\=\".+\"\n
^.+(cq|jcr)\:(created|lastReplicated|lastModified|lastReplicationAction|uuid)\=\".+\"\>
```

# Remove all `componentId` instances

```
^.+(componentId)\=\".+\"\n
^.+(componentId)\=\".+\"\>
```

Alternative Approach To Content Clearing
=======
Run the following snippet from the root of the directory whose content you want to clean. It will recursively search for `.content.xml` files.

Note: This uses `gsed` which and was tested on MacOS. You will probably need to replace with `sed` and tweak on other flavours of Linux.
```
find . -type f -name ".content.xml" -exec sh -c 'gsed -i "/cq:lastModified=/d" {} && gsed -i "/cq:lastModifiedBy=/d" {} && gsed -i "/cq:lastReplicationAction=/d" {} && gsed -i "/cq:lastReplicatedBy=/d" {} && gsed -i "/cq:lastReplicated=/d" {} && gsed -i "/jcr:createdBy=/d" {} && gsed -i "/jcr:lastModified=/d" {} && gsed -i "/jcr:lastModifiedBy=/d" {} ' \;
```
