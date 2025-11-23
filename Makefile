# Define variables for easy maintenance
JFLAGS=-g
JAVAC=javac
JAVA=java

# Directories
BIN_DIR=bin
SRC_DIR=src
TEST_DIR=unitTest
TEST_BIN_DIR=$(TEST_DIR)/bin # FIXED: No space after =

# Source Files
SRC_CLASSES=$(SRC_DIR)/App.java $(SRC_DIR)/Element.java $(SRC_DIR)/Grid.java
TEST_CLASSES=$(TEST_DIR)/GridTest.java $(TEST_DIR)/Test.java # FIXED: No space after =

# Executable Scripts
APP_SCRIPT=./app
TEST_SCRIPT=./test

# ----------------------------------------------------------------------
# Default Target: Build the application
# ----------------------------------------------------------------------
.PHONY: all app build_app
all: app

# Target 'app': The user command to build and prepare the application runner.
app: build_app $(APP_SCRIPT)

# Rule 'build_app': Compiles application source files into bin/
build_app:
	@echo "Compiling application source files into $(BIN_DIR)..."
	mkdir -p $(BIN_DIR)
	$(JAVAC) $(JFLAGS) -d $(BIN_DIR) $(SRC_CLASSES)

# Rule to create the application run script
$(APP_SCRIPT): 
	@echo "Creating app runner script $(APP_SCRIPT)..."
	@rm -f $(APP_SCRIPT) # Added safeguard to remove old file
	@echo '#!/bin/bash' > $(APP_SCRIPT)
	@echo '$(JAVA) -cp $(BIN_DIR) App "$$@"' >> $(APP_SCRIPT)
	@chmod +x $(APP_SCRIPT)
	@echo "Created executable: $(APP_SCRIPT)"

# ----------------------------------------------------------------------
# Testing Target: Build and prepare tests
# ----------------------------------------------------------------------
.PHONY: test build_test 
# Target 'test': The user command to build and prepare the test runner.
test: app build_test $(TEST_SCRIPT)

# Rule 'build_test': Compiles test source files into unitTest/bin
build_test:
	@echo "Compiling test source files into $(TEST_BIN_DIR)..."
	mkdir -p $(TEST_BIN_DIR)
	# Compile tests using application code in BIN_DIR on the classpath
	$(JAVAC) $(JFLAGS) -d $(TEST_BIN_DIR) -cp $(BIN_DIR) $(TEST_CLASSES)

# Rule to create the test run script (named ./test)
$(TEST_SCRIPT): 
	@echo "Creating test runner script $(TEST_SCRIPT)..."
	@rm -f $(TEST_SCRIPT) # Safe deletion of old file to prevent directory conflict
	@echo '#!/bin/bash' > $(TEST_SCRIPT)
	# Classpath must include both the main application code (bin) and test code (unitTest/bin)
	@echo '$(JAVA) -cp $(BIN_DIR):$(TEST_BIN_DIR) Test' >> $(TEST_SCRIPT)
	@chmod +x $(TEST_SCRIPT)
	@echo "Created executable: $(TEST_SCRIPT)"

# ----------------------------------------------------------------------
# Cleanup Rules
# ----------------------------------------------------------------------
.PHONY: clean cleanTest
clean: 
	@echo "Cleaning application artifacts..."
	@rm -rf $(BIN_DIR)
	@rm -f $(APP_SCRIPT)

cleanTest: 
	@echo "Cleaning test artifacts..."
	@rm -rf $(TEST_BIN_DIR)
	@rm -f $(TEST_SCRIPT)


