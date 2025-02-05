##!/bin/bash
#
## Path to your generated classes
#GENERATED_PATH="target/generated-sources/openapi/src/main/java/com/sky/cloud/dto"
#
## Loop through all Java files in the directory
#find $GENERATED_PATH -name "*.java" | while read -r file; do
#    # Check if the class already has @Entity annotation
#    if ! grep -q "@Entity" "$file"; then
#        # Add the @Entity annotation just before the class declaration
#        sed -i '' '/public class /i\
#@Entity\
#' "$file"
#        echo "Added @Entity to $file"
#    fi
#
#    # Check if the field 'id' already has @Id annotation
#    if ! grep -q "@Id" "$file"; then
#        # Add the @Id annotation just before the 'id' field
#        sed -i '' '/private Integer id;/i\
#@Id\
#' "$file"
#        echo "Added @Id to the id field in $file"
#    fi
#done
