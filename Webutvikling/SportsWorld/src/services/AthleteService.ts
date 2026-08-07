import axios from "axios";
import { type IAthlete } from "../interfaces/IAthlete";
import type {  IAthletesResponse, IAthleteResponse, IDefaultResponse } from "../interfaces/ResponseInterfaces";

const endpoint = "http://localhost:5077/api/athlete";

const getAllAthlete = async (): Promise<IAthletesResponse> => {
    try {
    const response = await axios.get(endpoint);
    console.log("TRY get");
    return {
      success: true,
      data: response.data,
    };
  } catch {
    console.log("CATCH");
    return {
      success: false,
      data: null,
    };
  }
};

const getAthleteById = async (id: number): Promise<IAthleteResponse> => {
  try {
    const response = await axios.get(`${endpoint}/${id}`)
    return {
      success: true,
      data: response.data
    }
  } catch {
    return {
      success: false,
      data: null
    }
  }
}

const postAthlete = async (athlete: IAthlete): Promise<IAthleteResponse> => {
  try {
    const response = await axios.post(endpoint, athlete);
    return {
      success: true,
      data: response.data
    }
  } catch {
    return {
      success: false,
      data: null
    }
  }
}

const deleteAthlete = async (id: number): Promise<IDefaultResponse> => {
  try {
    const response = await axios.delete(`${endpoint}/${id}`);
    return {
      success: true
    }
  } catch {
    return {
      success: false
    }
  }
}

const putAthlete = async (editedAthlete: IAthlete): Promise<IDefaultResponse> => {
  try {
    const response = await axios.put(endpoint, editedAthlete);
    if (response.status === 200) {
      return {
        success: true
      }
    } else {
      return {
        success: false
      }
    }
  } catch {
    return {
      success: false
    }
  }
}


export default { getAllAthlete, getAthleteById, postAthlete, deleteAthlete, putAthlete };
