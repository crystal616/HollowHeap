# HollowHeap
Hollow Heap Priority Queue

Tasks:

1. Create the visual space and interactive environment for the user;
2. Establish 3 hollow heap priority queues of the multi-root version — one for each color;
3. Establish a hollow heap priority queue of the single root version containing all objects in priority order by size;
4. Create the initial selection of random-sized and colored objects to be initially placed by the user (they do not need to be placed in the priority queues)
5. Populate the four priority queues with objects and keep the priority queues sufficiently ample to add more elements;
6. Each object after the initially generated objects will reside in 2 heaps plus an additional location that will keep track of those 2 locations: that is an object will have an occurrence in the size heap and an occurrence in its color heap as well as the list of “locations”;
7. An object that is placed because of color (highest priority that fits somewhere) will cause its entries in the list and the size heap and color heap to be “deleted”; similarly for an object placed because of size (highest priority that fits somewhere).

Note: total area of these initial shapes cannot exceed the visual space area and should allow empty spaces after their placement.
