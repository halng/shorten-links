"use client";
import React, { useState } from "react";

export default function Home() {
  const [url, setUrl] = useState("");
  const [shortLink, setShortLink] = useState("");

  function handleSubmit(e: any) {
    if (shortLink !== "") {
      navigator.clipboard.writeText(`http://localhost:3000/${shortLink}`);
      setShortLink("");
      return;
    }
    e.preventDefault();
    fetch(`http://localhost/api/v1/shorten?originLink=${url}`)
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        console.log(data);
        setShortLink(data.shortLink);
      })
      .catch((err) => {
        console.log(err);
      });
  }
  return (
    <div className="form__group field">
      <input
        type="input"
        className="form__field"
        placeholder="URL here"
        name="name"
        id="name"
        required
        onChange={(e) => setUrl(e.target.value)}
      />
      <label htmlFor="name" className="form__label">
        URL here
      </label>

      <button type="submit" className="form__submit" onClick={handleSubmit}>
        {shortLink !== "" ? "Click to copy" : "Get"}
      </button>
    </div>
  );
}
