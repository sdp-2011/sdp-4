if [[ -f worldstate_pb2.py ]]; then
	echo "Removing existing Python source files..."
	rm worldstate_pb2.py
fi

if [[ -d uk ]]; then
	echo "Removing existing Java source files..."
	rm -r uk
fi

echo "Generating source files..."
protoc -I=. --python_out=. --java_out=. worldstate.proto
