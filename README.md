# Portfolio-Simulator

<img width="1470" alt="image" src="https://github.com/user-attachments/assets/ec189e6b-9536-40b2-8b76-328c14c9a3f0" />


## Overview
Portfolio Simulator is a full-stack application that allows users to manage a simulated investment portfolio. Users can register, log in, track their investments, view PnL (Profit & Loss) for sold stocks.

## Features
- **User Authentication**: Secure registration and login system.
- **Portfolio Management**: Track and manage simulated investments.
- **Stock Search Suggestions**: Returns stock auto complete suggestions in buy/sell prompts.
- **PnL Calculation**: Profit and loss tracking for sold stocks.
- **Responsive**: Completely responsive frontend.
- **Full-Stack Architecture**:
  - **Frontend**: Built using plain HTML, CSS, and JavaScript.
  - **Frontend Server**: Node.js with Express.
  - **Backend**: Micronaut for efficient API handling.

## Tech Stack
- **Frontend**: HTML, CSS, JavaScript
- **Frontend Server**: Node.js, Express
- **Backend**: Micronaut, Java
- **Database**: PostgreSQL
- **Authentication**: JWT

## Installation

### Prerequisites
Ensure you have the following installed:
- Node.js
- Java (for Micronaut backend)
- PostgreSQL

### Steps
1. **Clone the repository**
   ```sh
   git clone https://github.com/yourusername/portfolio-simulator.git
   cd portfolio-simulator
   ```

2. **Set up frontend**
   Serve the HTML, CSS, and JavaScript files using any static file server.

3. **Set up frontend server**
   ```sh
   cd frontend-server
   npm install
   node server.js
   ```

4. **Set up backend**
   ```sh
   cd backend
   ./gradlew run
   ```

5. **Access the application**
   - Open `http://localhost:3000` to view the frontend.
   - Ensure backend and frontend servers are running for full functionality.

## Usage
1. Register and log in.
2. Navigate to the portfolio.
3. Check PnL to track profit and loss for sold stocks.
4. Add or Sell Stocks

## Contribution
<a href="https://github.com/maverick-1729"> Bhavya Savalia</a> -> Made an API for suggesting auto complete in buy and sell stocks using Tries, Levenshtein Distance and N-grams.

