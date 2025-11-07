
from fastapi import FastAPI, UploadFile, Form, HTTPException
from fastapi.middleware.cors import CORSMiddleware
import pandas as pd
import requests
import io

app = FastAPI()


# Configurar CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  
    allow_credentials=True,
    allow_methods=["*"],  # Permite POST, OPTIONS, GET, etc.
    allow_headers=["*"],  # Permite todas las cabeceras (Authorization, Content-Type, etc.)
)

SPRING_BOOT_API_URL = "http://unijobs-api:8080/api/students"

@app.post("/procesar-notas")
async def procesar_notas(
    file: UploadFile, 
    aplicante_id: int = Form(...),
    auth_token: str = Form(...)
):
        
    try:
        df = pd.read_excel(file.file, header=0, engine="calamine")[["Código", "Materia", "Nota"]]

        df = df[~df["Nota"].str.contains("Ausen", case=False, na=False)].copy()
        df["Nota_num"] = df["Nota"].str.extract(r"(\d+)").astype(float)

        df = df.dropna(subset=['Nota_num'])
        df["Nota_num"] = df["Nota_num"].astype(int)

        materias = [
            {"code": str(row.Código), "name": row.Materia, "note": row.Nota_num}
            for _, row in df.iterrows()
        ]

    except Exception as e:
        raise HTTPException(status_code=400, detail=f"Error processing Excel file: {str(e)}")

    payload = materias 
    
    endpoint_url = f"{SPRING_BOOT_API_URL}/{aplicante_id}/subjects"
    headers = {
        "Authorization": f"Bearer {auth_token}",
        "Content-Type": "application/json"
    }

    try:
        response = requests.post(endpoint_url, json=payload, headers=headers)
        
        response.raise_for_status() 

        return {
            "message": "Subjects sent and processed successfully by backend",
            "loaded_subjects": response.json()
        }

    except requests.exceptions.HTTPError as e:
        if e.response.status_code == 401:
            raise HTTPException(status_code=401, detail="Authentication error. Token is invalid or expired.")
        if e.response.status_code == 404:
            raise HTTPException(status_code=404, detail=f"Applicant with ID {aplicante_id} not found in backend.")
        
        raise HTTPException(status_code=e.response.status_code, detail=f"Backend error: {e.response}")
    
    except requests.exceptions.RequestException as e:
        raise HTTPException(status_code=503, detail=f"Could not connect to Spring Boot backend. Is it running? Detail: {str(e)}")

