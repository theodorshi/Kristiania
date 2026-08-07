import axios from "axios";
import { type IDefaultResponse } from "../interfaces/ResponseInterfaces";

const endpointImage = "http://localhost:5077/api/image"

const uploadImage = async (image: File): Promise<IDefaultResponse> => {
    try {

        const formData = new FormData();
        formData.append("file", image);

        const response = await axios({
            url: endpointImage,
            method: "POST",
            data: formData,
            headers: {"Content-Type": "multipart/form-data"}
        })

        formData.delete("file")
   
        return {
            success: true
        }
        }
    catch {
        return {
            success: false
        }
        }
} 

export default { uploadImage}