#!/bin/bash

echo "Running add-entity-annotations script..."

# Paths to your generated classes
GENERATED_PATHS=("target/generated-sources/openapi/src/main/java/com/sky/cloud/dto"
                 "target/generated-sources/openapi/src/main/java/com/sky/cloud/dto2")

# Loop through both directories
for GENERATED_PATH in "${GENERATED_PATHS[@]}"; do
    echo "Processing directory: $GENERATED_PATH"

    # Loop through all Java files in the current directory
    find "$GENERATED_PATH" -name "*.java" | while read -r file; do
        echo "Processing file: $file"  # Debugging output

        # Add necessary imports if not already present
        if ! grep -q "import jakarta.persistence.Entity;" "$file"; then
            sed -i '' '/package /a\
import jakarta.persistence.Entity;' "$file"
            echo "Added import for @Entity to $file"
        fi

        if ! grep -q "import jakarta.persistence.Id;" "$file"; then
            sed -i '' '/package /a\
import jakarta.persistence.Id;' "$file"
            echo "Added import for @Id to $file"
        fi

        if ! grep -q "import jakarta.persistence.GeneratedValue;" "$file"; then
            sed -i '' '/package /a\
import jakarta.persistence.GeneratedValue;' "$file"
            echo "Added import for @GeneratedValue to $file"
        fi

        if ! grep -q "import jakarta.persistence.GenerationType;" "$file"; then
            sed -i '' '/package /a\
import jakarta.persistence.GenerationType;' "$file"
            echo "Added import for @GenerationType to $file"
        fi

        # Add @Entity annotation before class declaration if not present
        if ! grep -q "@Entity" "$file"; then
            sed -i '' '/public class /i\
@Entity\
' "$file"
            echo "Added @Entity to $file"
        fi

        # Ensure @Id and @GeneratedValue are correctly placed above the id field
        if grep -q "private Integer id;" "$file"; then
            if ! grep -q "@Id" "$file"; then
                sed -i '' '/private Integer id;/i\
@Id\
@GeneratedValue(strategy = GenerationType.AUTO)\
' "$file"
                echo "Added @Id and @GeneratedValue to the id field in $file"
            elif ! grep -q "@GeneratedValue" "$file"; then
                sed -i '' '/@Id/a\
@GeneratedValue(strategy = GenerationType.AUTO)' "$file"
                echo "Added @GeneratedValue to the id field in $file"
            fi
        fi
    done
done

echo "Script execution completed."
