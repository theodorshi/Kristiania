DROP DATABASE IF EXISTS ScrapyardDB;
CREATE DATABASE ScrapyardDB;

USE ScrapyardDB;

CREATE TABLE Scrapyard (
    ScrapyardID INT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Address VARCHAR(255) NOT NULL,
    PhoneNumber VARCHAR(20) NOT NULL
);

CREATE TABLE FossilCar (
    VehicleID INT PRIMARY KEY,
    Brand VARCHAR(255) NOT NULL,
    Model VARCHAR(255) NOT NULL,
    YearModel INT NOT NULL,
    RegistrationNumber VARCHAR(20) NOT NULL,
    ChassisNumber VARCHAR(20) NOT NULL,
    Driveable BOOLEAN NOT NULL,
    NumberOfSellableWheels INT NOT NULL,
    ScrapyardID INT,
    FuelType VARCHAR(50) NOT NULL,
    FuelAmount INT NOT NULL,
    FOREIGN KEY (ScrapyardID) REFERENCES Scrapyard(ScrapyardID)
);

CREATE TABLE ElectricCar (
    VehicleID INT PRIMARY KEY,
    Brand VARCHAR(255) NOT NULL,
    Model VARCHAR(255) NOT NULL,
    YearModel INT NOT NULL,
    RegistrationNumber VARCHAR(20) NOT NULL,
    ChassisNumber VARCHAR(20) NOT NULL,
    Driveable BOOLEAN NOT NULL,
    NumberOfSellableWheels INT NOT NULL,
    ScrapyardID INT,
    BatteryCapacity INT NOT NULL,
    ChargeLevel INT NOT NULL,
    FOREIGN KEY (ScrapyardID) REFERENCES Scrapyard(ScrapyardID)
);

CREATE TABLE Motorcycle (
    VehicleID INT PRIMARY KEY,
    Brand VARCHAR(255) NOT NULL,
    Model VARCHAR(255) NOT NULL,
    YearModel INT NOT NULL,
    RegistrationNumber VARCHAR(20) NOT NULL,
    ChassisNumber VARCHAR(20) NOT NULL,
    Driveable BOOLEAN NOT NULL,
    NumberOfSellableWheels INT NOT NULL,
    ScrapyardID INT,
    HasSidecar BOOLEAN NOT NULL,
    EngineCapacity INT NOT NULL,
    IsModified BOOLEAN NOT NULL,
    NumberOfWheels INT NOT NULL,
    FOREIGN KEY (ScrapyardID) REFERENCES Scrapyard(ScrapyardID)
);
