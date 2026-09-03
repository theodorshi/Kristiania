import axios from "axios";
import type { IFinance } from "../interfaces/IFinance";
import type { IFinanceResponse } from "../interfaces/ResponseInterfaces";
import type { IDefaultResponse } from "../interfaces/ResponseInterfaces";


const endpoint = "http://localhost:5077/api/finance";

const getFinance = async (): Promise<IFinanceResponse> => {
    try {
    const response = await axios.get(endpoint);
    console.log("TRY get finance");
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


const putFinance = async (editedFinance: IFinance): Promise<IDefaultResponse> => {
  try {
      const response = await axios.put(endpoint, editedFinance);
      if (response.status === 200) { 
          return {
              success: true,
            };
      } else {
          return {
              success: false,
          }
        }
  } catch {
    return {
      success: false,
    }
  }
}

export default{ getFinance, putFinance };
