# 👑 LeBron Chatbot - The King's Task Manager 🏀

**"Nothing easy! Greatness takes time and effort."** - LeBron James

A basketball-themed task management chatbot inspired by LeBron James, designed to help you dominate your daily tasks like the King dominates the court.

## 🎯 Features

### Task Management
- **Todo Tasks**: Simple tasks without deadlines
- **Deadline Tasks**: Tasks with specific due dates
- **Event Tasks**: Tasks with start and end dates
- **Mark/Unmark**: Track completion status
- **Delete**: Remove tasks from your list
- **Find**: Search tasks by keywords
- **Due**: Filter tasks by due date

### LeBron-Themed Experience
- 🏀 Basketball-inspired UI with gold and black color scheme
- 👑 King James personality and responses
- 🎨 Custom CSS styling with Lakers/Heat/Cavaliers colors
- 💬 Interactive chat interface with LeBron's signature phrases

### Data Persistence
- Automatic saving to CSV file
- Tasks persist between sessions
- File-based storage system

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- JavaFX SDK
- Gradle (optional, for dependency management)

### Installation

1. **Clone the repository**
```bash
git clone [your-repository-url]
cd lebron-chatbot
```

2. **Set up JavaFX**
   - Download JavaFX SDK from [OpenJFX website](https://openjfx.io/)
   - Add JavaFX to your module path

### Running the Application

**Option 1: Through Main.java**
```bash
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml gui.Main
```

**Option 2: Through Lebron.java (Console mode)**
```bash
java lebron.Lebron
```

## 📖 Usage Guide

### Basic Commands

| Command | Format | Example |
|---------|--------|---------|
| **Help** | `help` | `help` |
| **List** | `list` | `list` |
| **Todo** | `todo <description>` | `todo Practice free throws` |
| **Deadline** | `deadline <description> /by YYYY-MM-DD` | `deadline Submit report /by 2024-12-31` |
| **Event** | `event <description> /from YYYY-MM-DD /to YYYY-MM-DD` | `event Lakers game /from 2024-12-25 /to 2024-12-25` |
| **Mark** | `mark <task_number>` | `mark 1` |
| **Unmark** | `unmark <task_number>` | `unmark 1` |
| **Delete** | `delete <task_number>` | `delete 1` |
| **Find** | `find <keyword>` | `find basketball` |
| **Due** | `due [YYYY-MM-DD]` | `due 2024-12-31` |

### Sample Conversation
```
LeBron: 👑 Wassup, I'm LeBron! The King is here to help you dominate your tasks...

You: todo Practice shooting
LeBron: King James has added a new task:
[T][ ] Practice shooting
Now you have 1 tasks in the list

You: deadline Game day preparation /by 2024-12-25
LeBron: King James is setting a deadline!
[D][ ] Game day preparation (by: Dec 25, 2024)
Nothing easy! Deadline set!

You: mark 1
LeBron: Crown this task! Marked as done:
[T][X] Practice shooting
```

## 🏗️ Project Structure

```
src/
├── main/java/
│   ├── gui/                    # JavaFX GUI components
│   │   ├── DialogBox.java      # Chat message containers
│   │   ├── Main.java           # Application entry point
│   │   └── MainWindow.java     # Main GUI controller
│   ├── lebron/
│   │   └── Lebron.java         # Core chatbot logic
│   ├── parser/
│   │   └── Parser.java         # Command parsing and execution
│   ├── storage/
│   │   └── Storage.java        # File I/O operations
│   ├── tasks/                  # Task implementations
│   │   ├── Task.java           # Abstract task class
│   │   ├── ToDoTask.java       # Simple todo tasks
│   │   ├── DeadlineTask.java   # Tasks with deadlines
│   │   └── EventTask.java      # Tasks with date ranges
│   └── ui/
│       └── Ui.java             # Console UI handler
└── main/resources/
    ├── css/
    │   └── main.css            # Basketball-themed styling
    ├── images/                 # User and LeBron avatars
    ├── view/                   # FXML layout files
    └── help/
        └── help.txt            # Command documentation
```

## 🎨 Customization

### Themes
The application supports multiple team-inspired color schemes:
- **Lakers Theme**: Purple and gold
- **Heat Theme**: Red and orange
- **Cavaliers Theme**: Wine and gold

### Adding New Commands
1. Add new enum value to `Command` enum in `Parser.java`
2. Implement the `execute()` method
3. Add corresponding help text

### Modifying Responses
Update the response messages in `Parser.java` to customize LeBron's personality and phrases.

## 🔧 Technical Details

### Architecture
- **MVC Pattern**: Clear separation of model (tasks), view (GUI), and controller (parser)
- **Command Pattern**: Each user command is handled by a specific command implementation
- **Observer Pattern**: GUI updates automatically when tasks change

### Data Storage
- Tasks are saved to `./data/userData.csv`
- CSV format: `TaskType,Name,IsCompleted,Date1,Date2`
- Automatic backup and recovery

### Error Handling
- Graceful handling of invalid dates
- User-friendly error messages with LeBron's personality
- File I/O error recovery

## 🐛 Troubleshooting

### Common Issues

**JavaFX Module Errors**
```bash
# Make sure JavaFX modules are properly loaded
--add-modules javafx.controls,javafx.fxml
```

**CSS Not Loading**
- Verify CSS file path: `/css/main.css`
- Check file exists in resources folder
- Ensure CSS is properly linked in Main.java

**Images Not Displaying**
- Place images in `src/main/resources/images/`
- Supported formats: JPG, PNG
- Recommended size: 100x100 pixels

**Data File Issues**
- Check write permissions in project directory
- Verify CSV format if editing manually
- Delete corrupted file to reset data

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style
- Follow Java naming conventions
- Add JavaDoc comments for public methods
- Maintain LeBron-themed messaging consistency
- Test both GUI and console modes

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🏀 Acknowledgments

- Inspired by LeBron James' greatness and leadership
- JavaFX community for GUI framework
- Basketball fans worldwide for the motivation

## 📞 Support

Having trouble? Remember what LeBron says: **"I like criticism. It makes you strong."**

- Check the [Issues](../../issues) page
- Review the help command in the application
- Consult the troubleshooting section above

---

**"Strive for greatness." - LeBron James** 👑🏀

Made with 💜 and ☕ for task management champions everywhere.