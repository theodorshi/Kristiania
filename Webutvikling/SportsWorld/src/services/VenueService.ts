import axios from "axios";
import type { IVenue } from "../interfaces/IVenue";
import type {  IVenuesResponse, IVenueResponse, IDefaultResponse } from "../interfaces/ResponseInterfaces";

const endpoint = "http://localhost:5077/api/venue";

const getAllVenue = async (): Promise<IVenuesResponse> => {
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

const getVenueById = async (id: number): Promise<IVenueResponse> => {
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

const postVenue = async (venue: IVenue): Promise<IVenueResponse> => {
  try {
    const response = await axios.post(endpoint, venue);
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

const deleteVenue = async (id: number): Promise<IDefaultResponse> => {
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

const putVenue = async (editedVenue: IVenue): Promise<IDefaultResponse> => {
  try {
    const response = await axios.put(endpoint, editedVenue);
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
export default {getAllVenue,getVenueById,postVenue,deleteVenue,putVenue}