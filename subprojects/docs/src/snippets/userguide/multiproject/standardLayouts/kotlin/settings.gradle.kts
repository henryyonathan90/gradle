// tag::hierarchical-layout[]
include("project1", "project2:child", "project3:child1")
// end::hierarchical-layout[]

// tag::flat-layout[]
includeFlat("project3", "project4")
// end::flat-layout[]
