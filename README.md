# CPU Scheduler

## Project Description

Build a simulator that simulates processor execution for processes. Implement the simulator using object-oriented programming principles and consider that it may be extended in the future.

## Notable Project Constraints

- 1 task per processor at a time
- Cycle time is 1 second
- All processors are synchronized (same clock)
- Scheduling is based on priority; if priority is equal, scheduling is LJF (Longest Job First), else Random Scheduling
- The simulator is non-preemptive
- Processor creation time is negligible
- Scheduler has a queue/buffer for incoming tasks/processes

## Tools

- IntelliJ IDEA Community Edition
- Microsoft Office
- Draw.io

## Structural Overview

The Processor Executor Project consists of a Simulator that executes the components it creates or is responsible for. The simulator operates based on configurations provided by the user via the console.

### Configuration

A singleton class called `Configuration` is responsible for fetching the input required to run the simulator on initialization. The configuration file specifies a path to a file containing task data. The `ReaderWriter` class uses this path to fetch, process, and sort task data into a Queue’s LinkedList by order of ascending creation date.

### ReaderWriter

This utility class reads the path provided by the `Configuration` line-by-line and processes the lines to extract task data, which is encapsulated and sorted into a LinkedList for use by the `Simulator`.

### Simulator

The `Simulator` is the core class that executes the project’s intent. It includes a Clock, Scheduler, and Processors/Cores. The clock data is used by the scheduler to manage processor tasks. The Observer design pattern is employed for loosely coupled interactions between components.

### Clock

The `Clock` has a ticking mechanism that sleeps for 1000ms (1s) per cycle. After each tick, the clock (being the observable object) notifies all observing objects (Processors & Scheduler).

### Processor

Processors observe the clock. Upon notification, they check if there is a task executing. If the task’s execution time is finished, the processor clears the task and generates a task report stored in Task History.

### TaskFactory & Task

A task represents a process, encapsulating executable software. The `TaskFactory` creates tasks based on their type, utilizing the factory design pattern. The task includes data from the input file and its execution capabilities.

### Task Report

The `TaskReport` class stores data about completed or terminated tasks. This report is used to build the final visual report and is stored inside the processors.

### Scheduler & PriorityLJFScheduler

The `Scheduler` inserts tasks into available processors and accepts the simulator as a reference for communication. It is an observer that executes tasks at the head of the priority queue. The scheduler is designed for scalability with different types.

### Report Generator

The `ReportGenerator` class generates a visual report (clock & processors) using the `Simulator` as input.

### Console, Message, MessageBuilder, AnsiColors

The `Console` class prints data to the system output. It uses the `Message` class created by the `MessageBuilder`, which utilizes the builder pattern. `Message` and `MessageBuilder` use the `AnsiColors` class for colored output.

### Surface Level UML Class Diagram

The diagram provides a high-level understanding of the project’s structure, generated using IntelliJ. It illustrates the main components and their relations, along with messaging and static classes.

## Behavioral Overview

### Initialize Console

Initialize a buffered reader to read the system’s input stream/console.

### Initialize Configuration

Prompt the user for data: processor count, cycle count, and task path.

### Generate Tasks

The `ReaderWriter` reads the input file, processes it, generates tasks in a linked list, and sorts them in ascending order based on creation cycle.

### Simulation

Simulates scheduling in a multi-core CPU. Creates main components, initializes the clock and processors, and sets up observers.

### Observer Set-up

Add the Scheduler and Processors as observers to the Clock. Due to a discrepancy in Java’s Observer pattern implementation, add observers in reverse order.

### Tick & Notify

The clock ticks until the max cycle is reached. After each tick, tasks suitable for scheduling are inserted into the scheduler. The clock notifies its observers, and all processors are cleared of tasks when the max cycle is reached.

### Notification Received by Processor(s)

The `Processor` checks for running tasks. If completed, it generates a task report and clears the task.

### Notification Received by Scheduler

The `Scheduler` checks its priority queue for tasks and assigns them to empty processors in order.

### Report Generation

Two reports are generated: the simulator timeline (from main components) and a final visual report from the `ReportGenerator`.

## Practices & Principles

### Design Patterns

- **Singleton**: Used in the `Configuration` class to ensure a single instance.
- **Factory**: `TaskFactory` creates tasks based on required executables.
- **Builder**: `MessageBuilder` constructs messages with optional attributes.
- **Observer**: The Clock notifies observers (Processors and Scheduler) of ticks.

### Clean Code

- **Method Names**: Readable and reveal intent, avoiding abbreviations and noise words.
- **Field Names**: Avoid ambiguity and adhere to camelCasing.
- **Single Responsibility Principle (SRP)**: Classes have a single responsibility where appropriate.
- **Class Method Cohesion**: Small number of instance variables, methods operate on these variables.
- **Code Smells**: No commented-out code, unused code, or naming inconsistencies. Avoids negative conditionals and uses enums instead of static constants. Base classes know nothing about their derivations.

### S.O.L.I.D Principles

- **Single Responsibility Principle**: Classes maintain singular responsibilities.
- **Open-Closed Principle**: Extensible without modification (e.g., factory design pattern).
- **Liskov Substitution Principle**: Composition is preferred over inheritance to avoid violations.
- **Interface Segregation Principle**: Not applicable (no custom interfaces).
- **Dependency Inversion Principle**: Depends on abstractions rather than concretions.
