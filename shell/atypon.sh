#!/bin/bash
display_help() {
    echo "Usage: ./script.sh [DIRECTORY_PATH] [OPTIONS]"
    echo "Description: This script searches for files with specific extensions in the given directory and its subdirectories,"
    echo "generates a comprehensive report that includes file details such as size, owner, permissions, last modified timestamp,"
    echo "and file names, groups the files by owner, sorts the file groups by the total size occupied by each owner, and saves the report in a file."
    echo
    echo "Arguments:"
    echo "  DIRECTORY_PATH  Path to the directory to search for files (mandatory)"
    echo
    echo "Options:"
    echo "  -e, --extensions EXTENSIONS  Search for files with the specified" 
}

if [ "$1" = "--help" ]; then
    display_help
    exit 0
fi

if [ -z "$1" ]; then
    echo "Error: Directory path is missing."
    echo "Please provide the directory path as an argument."
    echo "Run './script.sh --help' for more information."
    exit 1
fi

directory_path="$1"

if [ ! -d "$directory_path" ]; then
    echo "Error: Directory not found."
    echo "Please provide a valid directory path."
    exit 1
fi

if [ -z "$2" ]; then
    # Print all files in the specified directory
    find "$directory_path" -type f -exec echo {} \;
    echo "chose an extension that you want"
    echo "Run './script.sh --help' for more information."
    exit 0
fi

file_extensions=()
size_filter=""
permissions_filter=""
modified_filter=""



while [[ $# -gt 1 ]]; do
    key="$2"
    case $key in
        -e|--extensions)
            IFS=',' read -ra file_extensions <<< "$3"
            shift
            ;;
        *)
            echo "Unknown option: $key"
            echo "Run './script.sh --help' for more information."
            exit 1
            ;;
    esac
    shift
done

owner_files=()
owner_size=()
total_files=0
total_size=0
largest_file=""
smallest_file=""
most_recent_file=""
oldest_file=""
owner_filenames=()

while IFS= read -r -d '' file; do

    filename=$(basename "$file")
    extension="${filename##*.}"
    match_extension=0
    for ext in "${file_extensions[@]}"; do
        if [ "$ext" = "$extension" ]; then
           match_extension=1
           break
        fi
    done

if [ $match_extension -eq 1 ]; then

    
        file_info=$(stat -c "%s %U %a %y" "$file")
        read -r size owner permissions last_modified <<< "$file_info"

        report+="File: $file"$'\n'
        report+="Name: $filename"$'\n'
        report+="Size: $size bytes"$'\n'
        report+="Owner: $owner"$'\n'
        report+="Permissions: $permissions"$'\n'
        report+="Last Modified: $last_modified"$'\n'
        report+="-------------------------------------"$'\n'

        owner_index=-1
        for i in "${!owner_files[@]}"; do
            if [ "${owner_files[$i]}" = "$owner" ]; then
                owner_index=$i
                break
            fi
        done

        if [ $owner_index -eq -1 ]; then
            owner_files+=("$owner")
            owner_size+=("$size")
            owner_filenames+=("$filename")
        else
            owner_size[$owner_index]=$((owner_size[$owner_index] + size))
            owner_filenames[$owner_index]+=$'\n'"$filename"
        fi

        if [ -z "$largest_file" ] || [ "$size" -gt "$largest_file" ]; then
            largest_file="$size"
        fi

        if [ -z "$smallest_file" ] || [ "$size" -lt "$smallest_file" ]; then
            smallest_file="$size"
        fi

        if [ -z "$most_recent_file" ] || [[ "$last_modified" > "$most_recent_file" ]]; then
            most_recent_file="$last_modified"
        fi

        if [ -z "$oldest_file" ] || [[ "$last_modified" < "$oldest_file" ]]; then
            oldest_file="$last_modified"
        fi

        ((total_files++))
        ((total_size += size))
    fi
done < <(find "$directory_path" -type f -print0)


if [ $total_files -eq 0 ]; then
    echo "No files found with the specified extensions."
    exit 0
fi

n=${#owner_size[@]}
for ((i = 0; i < n-1; i++)); do
    for ((j = 0; j < n-i-1; j++)); do
        if [ "${owner_size[$j]}" -lt "${owner_size[$j+1]}" ]; then

            temp=${owner_size[$j]}
            owner_size[$j]=${owner_size[$j+1]}
            owner_size[$j+1]=$temp

            temp=${owner_files[$j]}
            owner_files[$j]=${owner_files[$j+1]}
            owner_files[$j+1]=$temp
        fi
    done
done



sorted_output=""
for ((i = 0; i < n; i++)); do
    owner=${owner_files[$i]}
    sorted_output+="Owner: $owner"$'\n'

    filenames=${owner_filenames[$i]}

    sorted_output+="Files: $filenames"$'\n'
    sorted_output+="Total Size: ${owner_size[$i]} bytes"$'\n'
    sorted_output+="---------------------------------------"$'\n'
done

summary_report="Summary Report:"
summary_report+="\nTotal File Count: $total_files"
summary_report+="\nTotal Size: $total_size bytes"
summary_report+="\nLargest File: $largest_file bytes"
summary_report+="\nSmallest File: $smallest_file bytes"
summary_report+="\nMost Recently Modified File: $most_recent_file"
summary_report+="\nOldest File: $oldest_file"

echo -e "$report"$'\n'"Sorted File Groups:"$'\n'"$sorted_output"$'\n'"$summary_report" > file_analysis.txt

echo "Report saved as 'file_analysis.txt'."
