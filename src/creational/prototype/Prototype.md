

**Prototype pattern Allows us to:**
- create an object upfront and designate as a prototype object.
- create other objects by copying the prototype object and make necessary modifications.

e.g a cover letter for making job applications. :) 

A prototype object should allow clients a way of making a copy.
Default object `clone()` method uses a shallow copy.

**Shallow copy:**
* Original top-level object and all it's primitive types are duplicated.
* Low-level objects that the top-level objects contains are not duplicated.
Only the references are copied (both the original and the cloned objects referes to the same copy of low-level object.)

**Deep copy:**
* duplicates both top-level and all the primitive types
* Any low level object that the top-level object contains are also duplicated. both the original and cloned object refers to two different low-level objects.

**Real-world examples**
  *	GUI frameworks: Cloning preconfigured widgets instead of rebuilding them from scratch.
  *	Game development: Copying enemies, bullets, or power-ups with minor variations.
  *	Document editors: Copying a styled text block or shape with all its formatting.
  *	Caching / Object pools: Prebuilt objects can be cloned rather than created every time.